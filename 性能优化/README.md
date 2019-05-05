# 性能优化

## 启动优化

* APP启动过程
    * 1.打开电源   引导芯片代码加载引导程序Boot Loader到RAM中去执行
    * 2.BootLoader把操作系统拉起来
    * 3.Linux内核启动开始系统设置，找到init.rc文件启动初始化进程(init进程，pid为1)
    * 4.init进程初始化和启动属性服务，之后开启Zygote进程(孵化进程)
    * 5.Zygote开始创建JVM(以上过程都是C++)并开启SystemServer 
    * 6.启动Binder线程池和SystemServerManager启动各种服务(ActivityMangerService、WindowManagerSevice、PacjageManagerService、CameraService.......)
    * 7.AMS启动Launcher
    * 8.当我们点击图标，进入到public final class Launcher extends Activity，执行onClick(View view)方法，会把这个应用的相关信息传入，先获取一个intent-->startActivtySafe(v,intent,tag)-->startActivity(v,intent,tag)-->startActivity(intent);
    * startActivity(intent)会开一个APP进程
    * ActivityThread.java作为入口，用attach开启app，再去加载application的onCreate方法。activitythread.attach(false)-->mgr.accachApplication(mAppThread)会通过远端进程去回调private void handleBIndApplication(AppBindData data)-->Application app = data.info.makeApplication(创建Application对象)-->mInstrumention.callApplicationOnCreate(app)-->app.onCreate()

* adb命令
	* adb shell dumpsys activity activities 查看当前activity
	* adb shell ps 查看进程
	* 查看启动时间
		* 4.4以前 adb shell am start -W com.dhcc.app/com.dhcc.app.activity.SplashActity
			* ThisTime:最后一个启动的Activity的启动耗时
			* TotalTime:自动的所有Activity的启动耗时
			* WaitTime:ActivityManagerService启动App的Activity的总时间(包括当前Activity的onPause()和自己Activity的启动)
			* AM路径tools\android-src\android-6.0.1_r1\frameworks\base\cmds\am\src\com\android\commands\am
			* Am.java 946行开始打印启动时间信息。其中一个result对象，在871行初始化，result=mAm.startActivtyAndWait(...)在这个初始化时就已经进行了时间的计算；在android-src\android-6.0.1_r1\frameworks\base\services\core\java\com\android\server\am\ActivityRecord.java文件中计算
void windowsDrawnLocked()-->reportLaunchTimeLocked(SystemClock.uptimeMillis())中完成时间的统计;
		* 4.4以后 在Logcat输入Display筛选系统日志，不过滤信息No Filters

* 黑白屏
	* 白屏 
	```
	<sytle name="AppTheme" parent="Theme.AppCampat.Light"> 
	```
   * 黑屏
   ```
   <sytle name="AppTheme">
   ```
   (在以前的老版本上有效，现在的版本上默认使用透明处理了)
   
   * 解决方法：
   		* 1.在自己的```<style name="AppTheme" parent="Theme.AppCompat.Light">```中加入windowsbackground
   		* 2.设置windowbackground为透明的```<item name="android:windowIsTranslucent">true</item>```
但这2种方法会有个问题，所有的activity启动都会显示
		* 3.单独做成一个主题,再在功能清单中的单独activity下设置```<activity android:theme="@style/AppTheme.Launcher"/>```
然后在程序中使用setTheme(R.style.AppTheme);让APP中所有的activity还是使用以前的样式，这样做就只有启动时才使用自己的样式
		
		``` Xml
		<style name="AppTheme.Launcher">
        	<item name="android:windowBackground">@drawable/bg</item>
    	</style>
    	<style name="AppTheme.Launcher1">
        	<item name="android:windowBackground">@drawable/bg</item>
    	</style>
    	<style name="AppTheme.Launcher2">
        	<item name="android:windowBackground">@drawable/bg</item>
    	</style>
		```
		* 4.QQ中的方法
		
		``` Xml
		<item name="android:windowDisablePreview">true</item>
		<item name="android:windowBackground">@null</item>
		```
		
* Trace工具分享代码执行时间

	``` Java 
	 Debug.startMethodTracing(filePath);
	 ......需要统计执行时间的代码
	 Debug.stopMethodTracing(); 
	```
	
	* adb pull /storage/emulated/0/app1.trace把文件拉出来，拖进AS中分析
	* 优化方案
		* 1.开启线程(不能有handler，不能有UI操作，对异步要求不高(因为线程是异步的))
		* 2.懒加载(用到的时候再去初始化，如网络，数据库操作)


## UI绘制优化
* CPU与GPU的工作流程
 ![Image text](https://raw.githubusercontent.com/zy654781296/Notes/master/images/20190428164524.png)
 * 按钮的显示流程
 	Button对象含有left,top,right,bottom,width,height等信息经过CPU金酸处理成为多维的向量图形-->GPU负责像素填充(栅格化)，GPU绘制图形
 	栅格化：是将向量图形格式表示的图形转换成位图以用于显示器
* 卡顿的原因
	* Android 系统每隔 16ms 发出 VSYNC 信号 (1000ms/60=16.66ms) ，触发对 UI 进行渲染， 如果每次渲染都成功这样就能够达到流畅的画面所需要的 60fps ，为了能够实现60fps ，这意味着计算渲染的大多数操作都必须在 16ms 内完成。
	* 当一帧画面渲染时间超过16ms的时候，垂直同步机制会让显示器硬件 **等待GPU完成栅格化** 渲染操作

* 如何解决卡顿
	* **CPU减少xml转换成对象的时间**
	* **GPU减少重复绘制的时间** 

* 什么是**过度绘制**
	* GPU的绘制过程，就跟刷墙一样，一层层的进行，16ms刷新一下，这样就会造成，图层的覆盖现象，即无用的图层还被绘制在底层，造成不必要的浪费
* 如何解决过度绘制
	* 自定义控件中onDraw方法做了过多的重复绘制
	* 布局层次太深，重叠性太强，用户看不到的区域GPU也进行了渲染
	
![Image text](https://raw.githubusercontent.com/zy654781296/Notes/master/images/20190428170040.png)

**蓝色**：过度绘制了一次，无过度绘制

**绿色**：过度绘制了二次

**淡红**：过度绘制了三次

**深红**：过度绘制了四次

* **布局优化**
	*  去掉默认的背景
	*  去掉容器的背景

* **布局优化原则**
	* 减少不必要的嵌套
	* 使用merger避免与父容器重叠
	* 使用include避免重复 

## 内存优化

### Java虚拟机
![Image text](https://raw.githubusercontent.com/zy654781296/Notes/master/images/20190428171148.png)
![Image text](https://raw.githubusercontent.com/zy654781296/Notes/master/images/20190428171309.png)
![Image text](https://raw.githubusercontent.com/zy654781296/Notes/master/images/20190428172042.png)

* GC垃圾回收器：
	* 引用计数算法
	缺点：互相引用容易出现计数器永不为0
	
	* 可达性分析算法
	![Image text](https://raw.githubusercontent.com/zy654781296/Notes/master/images/20190428180346.png)
		* 哪些对象可作为GC ROOT:
			* 虚拟机栈正在运行使用的引用静态属性
			* 常量
			* JNI引用的对象
		* GC是需要**2次扫描**才回收对象，所以我们可以用finalize去救活丢失引用的对象
		
		``` Java 
		static App a;
		@Override
		protected void finalize() throws Throwable {
			super.finalize();
			a = this;
		}
		
		```
		
		* 回收和引用类型有关系
			* 强引用(StrongReference)：Object obj=new Object();  强引用有引用变量指向时永远不会被垃圾回收，JVM宁愿抛出OutOfMemory错误也不会回收这种对象。如果想中断强引用和某个对象之间的关联，可以显示地将引用赋值为null，这样一来的话，JVM在合适的时间就会回收该对象。比如Vector类的clear方法中就是通过将引用赋值为null来实现清理工作的
			* 软引用(SoftReference)：内存不足时回收，存放一些重要性不是很强又不能随便让清除的对象，比如图片切换到后台不需要马上显示了.软引用可用来实现内存敏感的高速缓存,比如网页缓存、图片缓存等。使用软引用能防止内存泄露，增强程序的健壮性
			* 弱引用：第一次扫到了，就标记下来，第二次扫到直接回收
			* 虚引用：幽灵 幻影引用   不对生存造成任何影响，用于跟踪GC的回收通知
			
			
		* Java中提供这四种引用类型主要有两个目的
			* 第一是可以让程序员通过代码的方式决定某些对象的生命周期；
			* 第二是有利于JVM进行垃圾回收

	* 内存泄露的原因：一个长声明周期的对象持有一个短生门周期对象的引用。
			

* 分析内存的常用工具
	* meinfo；procstats；DDMS；MAT；Finder-Activity；LeakCanary；LeakInspector；top/procrank 

	
	
* 内存抖动(内存频繁的分配和回收，分配速度大于回收速度)最终会OOM	
* Java中常用的回收算法
	* 标记-清除算法(Mark-Sweep)
   ![Image text](https://raw.githubusercontent.com/zy654781296/Notes/master/images/20190430135219.png)
   最基础的垃圾收集算法，算法分为“标记”和“清除”两个阶段：首先标	记出所有需要回收的对象，在标记完成之后统一回收掉所有被标记的	对象。标记-清除算法的缺点有两个：首先，效率问题，标记和清除	效率都不高。其次，标记清除之后会产生大量的不连续的内存碎片，	空间碎片太多会导致当程序需要为较大对象分配内存时无法找到足够	的连续内存而不得不提前触发另一次垃圾收集动作。
	
	* 复制算法(Capying)
![Image text](https://raw.githubusercontent.com/zy654781296/Notes/master/images/20190430135503.png)
	将可用内存按容量分成大小相等的两块，每次只使用其中一块，当这块内存使用完了，就将还存活的对象复制到另一块内存上去，然后把使用过的内存空间一次清理掉。这样使得每次都是对其中一块内存进行回收，内存分配时不用考虑内存碎片等复杂情况，只需要移动堆顶指针，按顺序分配内存即可，实现简单，运行高效。复制算法的缺点显而易见，可使用的**内存降为原来一半**。

	* 标记压缩算法(Mark-Compact)
![Image text](https://raw.githubusercontent.com/zy654781296/Notes/master/images/20190430135849.png)
	标记-整理算法在标记-清除算法基础上做了改进，标记阶段是相同的标记出所有需要回收的对象，在标记完成之后不是直接对可回收对象进行清理，而是让所有存活的对象都向一端移动，在移动过程中清理掉可回收的对象，这个过程叫做整理。

		标记-整理算法相比标记-清除算法的优点是内存被整理以后不会产生大量不连续内存碎片问题。

		复制算法在对象存活率高的情况下就要执行较多的复制操作，效率将会变低，而在对象存活率高的情况下使用标记-整理算法效率会大大提高。
	
	* 分代收集算法 
	
	![Image text](https://raw.githubusercontent.com/zy654781296/Notes/master/images/20190430140525.png)
	![Image text ](https://raw.githubusercontent.com/zy654781296/Notes/master/images/20190430140729.png)
	
	根据内存中对象的存活周期不同，将内存划分为几块，java的虚拟机中一般把内存划分为新生代和年老代，当新创建对象时一般在新生代中分配内存空间，当新生代垃圾收集器回收几次之后仍然存活的对象会被移动到年老代内存中，当大对象在新生代中无法找到足够的连续内存时也直接在年老代中创建。
	
	Serial串行收集器</br>
	ParNew收集器</br>
	Parallel Scavenge收集器</br>
	Serial Old收集器</br>
	Parallel Old收集器</br>
		
### 优化内存的方式
1.数据类型

 * 不用使用比需求更占用空间的基本数据类型，比如整型的时候用int，不用long或者double


2.循环尽量用for-index，少用iterator，自动装箱尽量少用
 
 * 有3种循环方式
 	* Iterator
 	
 	``` Java 
 	for (Iterator it = list.iterator(); it.hasNext();){
 		Object o = it.next();
 		...
 	}
 	```
 	
 	* for-Index

 	``` Java 
 	for(int index = 0; index < 100; index++){
 		...
 	}
 	```
 	* Simplified  

 	``` Java 
 	for(Object o : list) {
 		...
 	}
	```

3.数据结构与算法的解度处理
	
  * 数据量在千级以下的 用SparseArray，ArrayMap 

4.枚举优化

``` Java
public class WEEK {

    public static final int MONDAY = 1;
    public static final int TUESDAY = 2;
    public static final int WEDNESDAY = 3;
    public static final int THURSDAY = 4;
    public static final int FRIDAY = 5;
    public static final int SATURDAY = 6;
    public static final int SUNDAY = 7;

    @Documented
    @IntDef(flag = true, value = {MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY})
    @Target({ElementType.PARAMETER,ElementType.METHOD,ElementType.FIELD})
    @Retention(RetentionPolicy.SOURCE)

    public @interface Model{

    }

    private @Model int value = MONDAY;
    public void setWeek(@Model int value) {
        this.value = value;
    }

    public @Model int getWeek() {
        return this.value;
    }

}
``` 

5.尽量使用static final

  * static会由编译器调用clinit方法进行初始化
  * static final不需要进行初始化工作，打包在dex文件中可以直接调用，并不会在类初始化申请内存所以基本数据类型的成员，可以全写成static final 

6.字符串的链接尽量少用+拼接

7.重复申请内存的问题

  * 同一个方法多次调用，如递归函数 ，回调函数中new对象,读流直接在循环中new对象等不要在onMeause()  onLayout() onDraw()  中去刷新UI（requestLayout）
  
8.避免GC回收将来用重用的对象

 * 内存设计模式对象池+LRU算法

9.Activity组件泄露

10.尽量使用IntentService，少用Service
	
	
	
	