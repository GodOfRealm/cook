一、第一次打包
    1、将app目录下tinker.gradle中：ext->tinkerEnable改为true
    2、执行：gradlew clean assembleReleaseChannels，一样流程打渠道包[]
    3、经过第二步后，app下面会生成tinker文件夹，里面有apk、mapping、R三个文件，
       必须保存下来，打补丁需以这三个文件为基础。
    4、第一次打包主要生成第3步的文件，并不需要打补丁。
       而后的每一次打包都需要保存基准包，也就是第3步的那三个文件。

二、打补丁
    1、先把基准包(3个文件)放到app目录下的tinker文件夹下，如果没有就创建。
    2、这三个文件名字要改成：pick_base.apk、pick_base-R.txt、pick_base-mapping.txt。
    3、打开tinker.gradle文件，修改patch_version变量值，
       默认从1开始，当前版本第一次打补丁就1，第二次就2，以此类推。
    4、同样在tinker.gradle文件下，修改oldBaseName变量值，oldBaseName为基准包的名字，比如基准包：pick_v1.1.0_2.apk，那么它的值为：pick_v1.1.0_2。
    5、as窗口中Gradle projects[右边]依次打开：pick->app->Tasks->tinker,然后点击tinkerPatchRelease
    6、等第4步执行完后，app->tinker下就会出现tinkerPatch文件夹，该文件夹下的patch_signed_7zip.apk就是我们所需要的补丁包了。
    
三、上传补丁包
    