# 性能优化

## 启动优化
    
* APP启动过程
    * 1.打开电源   引导芯片代码加载引导程序Boot Loader到RAM中去执行
    * 2.BootLoader把操作系统拉起来
    * 3.Linux内核启动开始系统设置，找到init.rc文件启动初始化进程(init进程，pid为1)
    * 4.init进程初始化和启动属性服务，之后开启Zygote进程(孵化进程)
    * 5.Zygote开始创建JVM(以上过程都是C++)并开始SystemServer 
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

* Java虚拟机
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
			

