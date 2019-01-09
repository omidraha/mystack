Git
===

https://github.com/k88hudson/git-flight-rules


Set push.default
----------------

warning: push.default is unset; its implicit value has changed in
Git 2.0 from 'matching' to 'simple'. To squelch this message
and maintain the traditional behavior, use:

  git config --global push.default matching

To squelch this message and adopt the new behavior now, use:

  git config --global push.default simple

When push.default is set to 'matching', git will push local branches
to the remote branches that already exist with the same name.

Since Git 2.0, Git defaults to the more conservative 'simple'
behavior, which only pushes the current branch to the corresponding
remote branch that 'git pull' uses to update the current branch.

See 'git help config' and search for 'push.default' for further information.
(the 'simple' mode was introduced in Git 1.7.11. Use the similar mode
'current' instead of 'simple' if you sometimes use older versions of Git)


.. code-block:: bash

    $ git config --global push.default simple


Untrack and stop tracking files in git
--------------------------------------

.. code-block:: bash

    $ git rm -r --cached .

Create new git project in bitbucket
-----------------------------------

.. code-block:: bash

    $ mkdir /path/to/your/new_project

    $ cd /path/to/your/new_project

    $ git init

    $ git remote add origin git@bitbucket.org:omidraha/new_project.git

    $ git push -u origin master



Remove local (untracked) files from current Git branch
------------------------------------------------------

http://git-scm.com/docs/git-clean

http://stackoverflow.com/questions/61212/remove-local-untracked-files-from-my-current-git-branch

.. code-block:: bash

    $ git clean

    # If the Git configuration variable clean.requireForce is not set to false,
    # git clean will refuse to run unless given -f, -n or -i.
    $ git clean -f

    # Remove untracked directories in addition to untracked files.
    $ git clean -f -d  # git clean -fd

    # Remove only files ignored by Git.
    # This may be useful to rebuild everything from scratch, but keep manually created files
    $ git clean -f -X  #  git clean -fX

Install Git
-----------


.. code-block:: bash

    $ sudo apt-get install git-core
    $ git --version

Configure Git
-------------

https://github.com/yui/yui3/wiki/Set-Up-Your-Git-Environment


.. code-block:: bash

    $ git config --global user.name "Omid Raha"
    $ git config --global user.email or@omidraha.com

    $ vim ~/.gitconfig

    [user]
        name = Omid Raha
        email = or@omidraha.com
    [push]
        default = simple
    [core]
        autocrlf = input
    [alias]
       st = status
       ci = commit
       co = checkout
       br = branch

    $ vim ~/.gitignore

    .DS_Store
    ._*
    .svn
    .hg
    .*.swp

git commit as different user
----------------------------

.. code-block:: bash

    $ git commit --author="Name <email>" -m "whatever"

Setting your username and email in Git
--------------------------------------

https://help.github.com/articles/setting-your-username-in-git/

https://help.github.com/articles/setting-your-email-in-git/

Git uses your username and email address to associate commits with an identity.

The ``git config`` command can be used to change your Git configuration, including your username and email address,
It takes two arguments:

    The setting you want to change--in this case, ``user.name`` or ``user.email``

    Your new name, for example, Billy Everyteen
    Your new email, for example, your_email@example.com

To set your username and email for a specific repository
++++++++++++++++++++++++++++++++++++++++++++++++++++++++

Enter the following command in the root folder of your repository:

.. code-block:: bash

    # Set a new name
    $ git config user.name "Billy Everyteen"

    # Set a new email
    $ git config user.email "your_email@example.com"

    # Verify the new name
    $ git config user.name
    # Billy Everyteen

    # Verify the new email
    $ git config user.name
    # your_email@example.com

To set your username and email for every repository on your computer
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    Navigate to your repository from a command-line prompt.

    Set your username and email with the following command.

    .. code-block:: bash

        $ git config --global user.name "Billy Everyteen"
        $ git config --global user.email "your_email@example.com"

    Confirm that you have set your username and email correctly with the following command.

    .. code-block:: bash

        $ git config --global user.name
        # Billy Everyteen

        $ git config --global user.email
        # # your_email@example.com

To set your username and email for a single repository
++++++++++++++++++++++++++++++++++++++++++++++++++++++

    Navigate to your repository from a command-line prompt.

    Set your username and email with the following command.

    .. code-block:: bash

        $ git config user.name "Billy Everyteen"
        $ git config user.email "your_email@example.com"

    Confirm that you have set your username and email correctly with the following command.

    .. code-block:: bash

        $ git config user.name
        # Billy Everyteen

        $ git config user.email
        # your_email@example.com


Setting up a git server
-----------------------

http://git-scm.com/book/en/v2/Git-on-the-Server-Setting-Up-the-Server

Let’s walk through setting up SSH access on the server side.
In this example, you’ll use the authorized_keys method for authenticating your users.
We also assume you’re running a standard Linux distribution like Ubuntu.
First, you create a git user and a .ssh directory for that user.

.. code-block:: bash

    $ sudo adduser git
    $ su git
    $ cd
    $ mkdir .ssh && chmod 700 .ssh
    $ touch .ssh/authorized_keys && chmod 600 .ssh/authorized_keys

Next, you need to add some developer SSH public keys to the authorized_keys file for the git user.
Let’s assume you have some trusted public keys and have saved them to temporary files.
Again, the public keys look something like this:

.. code-block:: bash

    $ cat /tmp/id_rsa.john.pub
    ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCB007n/ww+ouN4gSLKssMxXnBOvf9LGt4L
    ojG6rs6hPB09j9R/T17/x4lhJA0F3FR1rP6kYBRsWj2aThGw6HXLm9/5zytK6Ztg3RPKK+4k
    Yjh6541NYsnEAZuXz0jTTyAUfrtU3Z5E003C4oxOj6H0rfIF1kKI9MAQLMdpGW1GYEIgS9Ez
    Sdfd8AcCIicTDWbqLAcU4UpkaX8KyGlLwsNuuGztobF8m72ALC/nLF6JLtPofwFBlgc+myiv
    O7TCUSBdLQlgMVOFq1I2uPWQOkOWQAHukEOmfjy2jctxSDBQ220ymjaNsHT4kgtZg2AYYgPq
    dAv8JggJICUvax2T9va5 gsg-keypair

You just append them to the git user’s authorized_keys file in its .ssh directory:

.. code-block:: bash

    $ cat /tmp/id_rsa.john.pub >> ~/.ssh/authorized_keys


Now, you can set up an empty repository for them by running git init with the --bare option,
which initializes the repository without a working directory:

.. code-block:: bash

       $ cd /path/to/prj
       $ git init --bare sample_prj.git

Then, John, Josie, or Jessica can push the first version of their project into that repository
by adding it as a remote and pushing up a branch.
Note that someone must shell onto the machine and create a bare repository every time you want to add a project.

.. code-block:: bash

    # on Johns computer
    $ cd myproject
    $ git init
    $ git add .
    $ git commit -m 'initial commit'
    $ git remote add origin git@gitserver:/path/to/prj/sample_prj.git
    $ git push origin master

At this point, the others can clone it down and push changes back up just as easily:

.. code-block:: bash

    $ git clone git@gitserver:/path/to/prj/sample_prj.git
    $ cd project
    $ vim README
    $ git commit -am 'fix for the README file'
    $ git push origin master



How do you discard unstaged changes in Git?
-------------------------------------------


.. code-block:: bash

    $ git checkout -- .


http://stackoverflow.com/questions/52704/how-do-you-discard-unstaged-changes-in-git


Working on github API
---------------------

.. code-block:: bash

    $ pip install pygithub3

.. code-block:: python

    from pygithub3 import Github
    g = Github()
    repo = = g.repos.get('django','django')


How can one find good forks on GitHub?
--------------------------------------

http://forked.yannick.io

http://forked.yannick.io/django/django


IDE
---

http://www.syntevo.com/smartgit/download


Undo changes in one file
------------------------

.. code-block:: bash

    $ git checkout /path/of/changed/file


List local and remote  branches
-------------------------------

.. code-block:: bash

    $ git branch -a


List remote branches
--------------------

.. code-block:: bash

    $ git branch -r


List only local branches
------------------------

.. code-block:: bash

    $ git branch

With no arguments, existing branches are listed and the current branch will be highlighted with an asterisk.


Delete a Git branch both locally and remotely
---------------------------------------------

To remove a local branch from your machine:

.. code-block:: bash

    $ git branch -d  <Branch_Name>

The ``-D`` force deletes, ``-d`` gives you a warning if it's not already merged in.

To remove a remote branch from the server:

.. code-block:: bash

    # As of Git v1.7.0, you can delete a remote branch using
    $ git push origin --delete <branchName>
    # which is easier to remember than
    $ git push origin :<Branch_Name>

http://stackoverflow.com/a/2003515


Merge a git branch into master
------------------------------

.. code-block:: bash

    $ git checkout master
    $ git merge <Branch_Name>


Remove last commit from remote git repository
---------------------------------------------


.. code-block:: bash

    $ git pull
    # use `git update-ref -d HEAD` instead, if  it's initial git commit
    $ git reset HEAD^
    # now some committed files be unstage
    # we can do git checkout for those files
    # force-push the new HEAD commit
    $ git push origin +HEAD

http://stackoverflow.com/questions/8225125/remove-last-commit-from-remote-git-repository
https://stackoverflow.com/a/6637891


.. code-block:: bash

    $ git stash
    $ git status
    $ git stash list
    $ git stash apply

https://git-scm.com/book/en/v1/Git-Tools-Stashing


Undo the last commit from local
-------------------------------

.. code-block:: bash

    git reset --soft HEAD~

http://stackoverflow.com/a/927386


Revert to specific commit
-------------------------

.. code-block:: bash

    git reset 56e05fced #resets index to former commit; replace '56e05fced' with your commit code
    git reset --soft HEAD@{1} #moves pointer back to previous HEAD
    git commit -m "Revert to 56e05fced"
    git reset --hard #updates working copy to reflect the new commit
    git push


19 Tips For Everyday Git Use
----------------------------

http://www.alexkras.com/19-git-tips-for-everyday-use/


How to Write a Git Commit Message
---------------------------------

http://chris.beams.io/posts/git-commit/

https://gist.github.com/adeekshith/cd4c95a064977cdc6c50



Adding an existing project to GitHub using the command line
-----------------------------------------------------------

First create a new repository from github web site,

Then:

.. code-block:: bash

    git remote add origin https://github.com/<USER-NAME>/<PROJECT-NAME>.git
    git push -u origin master

Also if project does not exist on your local, create it with:

.. code-block:: bash

    echo "# <PROJECT-NAME>" >> README.md
    git init
    git add README.md
    git commit -m "first commit"
    git remote add origin https://github.com/<USER-NAME>/<PROJECT-NAME>.git
    git push -u origin master

Add tag
-------

https://git-scm.com/book/en/v2/Git-Basics-Tagging

Listing Your Tags
+++++++++++++++++

Listing the available tags in Git is straightforward. Just type git tag:

.. code-block:: bash

    $ git tag
    v0.1
    v1.3

Annotated Tags
++++++++++++++

Creating an annotated tag in Git is simple.
The easiest way is to specify -a when you run the tag command:

.. code-block:: bash

    $ git tag -a v1.4 -m "my version 1.4"
    $ git tag
    v0.1
    v1.3
    v1.4

Lightweight Tags
++++++++++++++++

Another way to tag commits is with a lightweight tag.
This is basically the commit checksum stored in a file – no other information is kept.
To create a lightweight tag, don’t supply the -a, -s, or -m option:

.. code-block:: bash

    $ git tag v1.4-lw
    $ git tag
    v0.1
    v1.3
    v1.4
    v1.4-lw
    v1.5

Tag an older commit in Git?
---------------------------

.. code-block:: bash

    git tag -a v1.2 9fceb02 -m "Message here"



Push a tag to a remote repository
---------------------------------

.. code-block:: bash

    $ git push --follow-tags

http://stackoverflow.com/questions/5195859/push-a-tag-to-a-remote-repository-using-git


Remove (delete) a tag
---------------------

.. code-block:: bash

    $ git push --delete origin tag_name
    #  delete the local tag
    $ git tag --delete tag_name


Github “fatal: remote origin already exists”
--------------------------------------------


http://stackoverflow.com/a/10904450

.. code-block:: bash

    $ git remote set-url origin git@github.com:ppreyer/first_app.git

Install specific git commit with pip
------------------------------------


.. code-block:: bash

    $ cat requirements.txt
        git+https://github.com/Tivix/django-rest-auth.git@976b3bbe4dded03552218c1022ee95d8bdf1176c

    $ pip install -r requirements.txt
        # It's a warning, not an error.
        Could not find a tag or branch '976b3bbe4dded03552218c1022ee95d8bdf1176c', assuming commit.

https://pip.pypa.io/en/stable/reference/pip_install/#git


Rewriting the most recent commit message
----------------------------------------


.. code-block:: bash

    $ git commit --amend
    $ git push --force


https://help.github.com/articles/changing-a-commit-message/


git subtrees
------------


.. code-block:: bash

    $ cd /to/root/of/one/project/
    $ git remote add sub-prj git@bitbucket.org:omidraha/sub-prj.git
    $ git subtree add --prefix=src/sub-prj sub-prj dev

To update subtree project:

.. code-block:: bash

    $ cd /to/root/of/one/project/
    $ git subtree pull -P  src/sub-prj sub-prj dev

https://medium.com/@v/git-subtrees-a-tutorial-6ff568381844#.b923kyieb

http://stackoverflow.com/questions/18661894/git-updating-subree-how-can-i-update-my-subtree


Git fetch remote branch
-----------------------

Checkout to a new remote branch that exists only on the remote, but not locally

.. code-block:: bash

    $ git fetch origin

http://stackoverflow.com/a/16608774

Sample release
--------------

Add tag and merge dev to mater

.. code-block:: bash

    git checkout dev
    git pull
    git tag -a 2.0.1 -m "2.0.1"
    git push --follow-tags

    git checkout master
    git pull
    git merge dev
    git push --follow-tags
    git checkout dev


Warning: push.default is unset; its implicit value is changing in Git 2.0
-------------------------------------------------------------------------

warning: push.default is unset; its implicit value is changing in
Git 2.0 from 'matching' to 'simple'. To squelch this message
and maintain the current behavior after the default changes, use:

.. code-block:: bash

  git config --global push.default matching

To squelch this message and adopt the new behavior now, use:

.. code-block:: bash

  git config --global push.default simple


matching means git push will push all your local branches to the ones with the same name on the remote.
This makes it easy to accidentally push a branch you didn't intend to.

simple means git push will push only the current branch to the one that git pull would pull from,
and also checks that their names match. This is a more intuitive behavior, which is why the default is getting changed to this.



https://stackoverflow.com/a/13148313

Fatal: The upstream branch of your current branch does not match the name of your current branch.
-------------------------------------------------------------------------------------------------

.. code-block:: bash

    git checkout rc
    git push

fatal: The upstream branch of your current branch does not match
the name of your current branch.  To push to the upstream branch
on the remote, use

.. code-block:: bash

    git push origin HEAD:v1.1

To push to the branch of the same name on the remote, use

.. code-block:: bash

    git push origin v0.2

Git keeps track of which local branch goes with which remote branch. When you renamed the remote branch,
git lost track of which remote goes with your local rc branch.
You can fix this using the --set-upstream-to or -u flag for the branch command.

.. code-block:: bash

    git branch -u origin/rc

https://stackoverflow.com/a/27261804


Abort the merge
---------------

.. code-block:: bash

    # git merge --abort


Track remote branch that doesn't exist on local
-----------------------------------------------

Sometimes remote branch is not tracked on local, and there is no the branch name on the local:

Related error: Git: cannot checkout branch - error: pathspec  did not match any file(s) known to git


.. code-block:: bash

    $ git branch
        master
        dev

    $ git branch -a

        remotes/origin/master
        remotes/origin/dev
        remotes/origin/rc


.. code-block:: bash

    $ git remote update
    $ git fetch --all
    $ git checkout --track remotes/origin/rc


.. code-block:: bash

    $ git branch
        master
        rc
        dev


Fix git remote fatal: index-pack failed
-----------------------------

Traceback:

.. code-block:: bash


    or@omid:~/ws$ git clone git@bitbucket.org:example/example.git
    Cloning into 'example'...
    remote: Counting objects: 39831, done.
    remote: Compressing objects: 100% (16929/16929), done.
    Connection to bitbucket.org closed by remote host. 163.00 KiB/s
    fatal: The remote end hung up unexpectedly
    fatal: early EOFs:  99% (39758/39831), 19.57 MiB | 166.00 KiB/s
    fatal: index-pack failed

Solution:

.. code-block:: bash

    $ git config --global core.compression 0
    $ git clone --depth 1 git@bitbucket.org:example/example.git
    # retrieve the rest of the clone
    $ git fetch --unshallow
    # or, alternately:
    $ git fetch --depth=2147483647
    $ git pull --all

