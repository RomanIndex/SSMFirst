# SSMFirst
SSM第一个版本，以company的为基础version

》》》》参考https://www.cnblogs.com/smfx1314/p/8426115.html

记录一下，第一次从本地上传项目至github的过程：
1、准备好要上传的项目，并事先删掉一些没用的，比如.classes文件

2、右键打开Git Bash Here

3、在命令行中输入 **git init** 把这个文件夹变成Git可管理的仓库，然后可以通过git status命令，查看下现在的状态，也可以不看，随你

4、输入**git add .**   (注意点号 . 前面有空格)，把所有文件提交上去

5、再使用命令**git commit -m "这里面写你的注释"**  把文件提交的本地仓库

#######下面就到了连接远程仓库（也就是连接Github）`
6、输入命令，正常一个账号是这样的 **git remote add origin git@github.com:RomanIndex/SSMFirst.git**

但由于我配置了两个账号，所以我的正确的是 git remote add origin git@github:RomanIndex/SSMFirst.git
原因：（以后补上）

如果报错：fatal: remote origin already exists.
只需要先执行 **git remote rm origin**就好，后面再继续第（5）步的操作

7、如果新建远程仓库 不是 空的，例如你勾选了 Initialize this repository with a README，
就要先通过 **git pull --rebase origin master** 先将内容合并一下，即将远程已有的文件下载到本地（这步有可能比较慢，要耐心等一下）

8、最后 **git push origin master** 将第（4）步commit的内容上传到github上！OK了！

第一次上传这样，后续操作还是建议用IDEA的，十分清晰方便，END。。。
2019-03-27 晚21:31

