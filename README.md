## git自己总结


##git diff    查看修改后未提交代码的内容

##git log     显示从最近到最远的提交日志

##git  版本回退


##git reset --hard HEAD^    回退到上一个版本


##上一个版本就是HEAD^，上上一个版本就是HEAD^^，当然往上100个版本写100个^比较容易数不过来，所以写成HEAD~100

##走了还想回来咋办？  办法其实还是有的，只要上面的命令行窗口还没有被关掉，你就可以顺着往上找啊找啊，找到那个append GPL的commit ##id是3628164...，于是就可以指定回到未来的某个版本：

##$ git reset --hard 3628164


##  哈哈哈我胡汉三又回来了，真牛   不要见笑，学点东西高兴

##现在我回退到了旧版，正好又停电了，命令行窗口关了，啊坑了，怎么办，有办法

git reflog  用来记录你的每一次命令，可以找到commit id

##哈哈输了一口气，太险了，总算回来了


小结

现在总结一下：

HEAD指向的版本就是当前版本，因此，Git允许我们在版本的历史之间穿梭，使用命令git reset --hard commit_id。

穿梭前，用git log可以查看提交历史，以便确定要回退到哪个版本。

要重返未来，用git reflog查看命令历史，以便确定要回到未来的哪个版本。


##   前面讲了我们把文件往Git版本库里添加的时候，是分两步执行的：

第一步是用git add把文件添加进去，实际上就是把文件修改添加到暂存区；

第二步是用git commit提交更改，实际上就是把暂存区的所有内容提交到当前分支。

ssh-keygen -t rsa -C "youremail@example.com"   生成自己的SSH Key


分支管理

git checkout -b dev   创建并切换到dev分支

git branch   查看当前分支  git branch命令会列出所有分支，当前分支前面会标一个*号。

git checkout 分支名    切换到你想去的分支

git merge dev 命令用于合并指定分支到当前分支

git branch -d dev   删除分支

git log --graph  可以看到分支合并图

bug分支   当我们在写代码写到一半是不能提交突然运维提了A级的紧急bug要很快修改了，啊啊啊头大，怎么办

git stash  把当前工作现场储藏起来，修完bug再来放出来，现在找到出现bug的分支，切临时分支修复bug，好改完了松了一口气，继续写代码

回来，刚才的工作现场去哪了  git status是空得

不急。。。


git stash list  用这个命令看看，哦，还在虚惊一场，但是需要恢复一下

git stash pop恢复并删除stash内容，再看看stash没了，好了，松了一口气

