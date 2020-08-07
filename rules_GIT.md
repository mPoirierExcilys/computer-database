
Rules for managing the development together
---

# 1 - Pull / Push

### Every morning

Before working on my local repository, I have to pull, at least, *develop* and the current branch I am working on.
So the steps are:
- choose the branch I want to pull as the current working branch: `git checkout the_branch`.
- pull the current branch I am working on: `git pull origin the_branch`. (The command line `git pull` is usefull to update the branches list)


### Every evening

Before leaving, I have to 
- choose the branch I want to pull as the current working branch.
- pull the current branch I am working on: `git pull origin my_branch`.
- deal with the eventual merge conflict locally before killing the distant repository.
- push all of my changes on the branch I am currently working on: `git push origin my_branch`.


**/!\ Never do a `git push` alone, whithout specifying the origin remote and a branch to push.**


# 2 - Branches

### *Master*

The branch *master* is only for the current working version of the application, that can be used by the client.

**You don't have to push or merge on *master*.**
The merge here will be done **only** at the end of a sprint. 
It would be the product that we render to the client.


### *Develop*

The branch *develop* is the branch of the version currently in development.

You can merge on *develop*, for example, when you have finish your development of a function or your task and you are sure that it is correctly integrated.


### *Your_branch*

Each time you are working on a task, you have to create a branch dedicated to this task (with an explicit name such as *BDD_creation*).
You will merge your branch on *develop* once you are sure of your changes.


### To resume

I have a task to do with some code to implement, so:
1. I pull the *develop* branch (in order to have the last approuved version of the code in my new branch).
2. I create a branch dedicated to this task: *BDD_creation*.
2. I work on it until I think my work is done.
2. I do a code review by a pair.
2. A pair can pull my branch *BDD_creation* and tests it.
2. If it is ok, I can make a merge request from my branch *BDD_creation* into *develop*.
2. Someone has to verify the merge request in order to allow it or not.
2. If the merge request is accepted, my work is merged on *develop*.

At the end of a sprint, after the sprint review, we can merge *develop* into *master* if it is bug free.


# 3 - Commits

When I commit some changes, I have to be vigilant with :
- The files I add: 
    - if possible, my commit has to be for one purpose only.
    - `git add .` ou `git add myfile1 myfile2 myfile3`.
- The message I commit:
    - It has to be in english.
    - It has to be relevant and clear on its purpose.
    - If possible, it has to be an action : *"implementing zoom functions"* or *"implement zoom functions"*.
    - `git commit -m "my message"`.

# 4 - Merge Request

I can do a merge request when I think my work is relevant and my branch is clean (all changes commited, up-to-date with the repository branch).
In order to, I can follow those steps:
- I update my local branch *develop*: 
    - `git checkout develop`
    - `git pull origin develop`
- I switch to *my_branch*: `git checkout my_branch`
- I merge *develop* into my_branch, in order to resolve the potential conflicts before doing a merge request in the repository
    - `git merge develop`
    - Resolve conflicts:
        - By verifying the code and choose what I want to keep :
          ```
          >>>>HEAD
            // blabla
          ========
            // blabla
          >>>>>055abc56561def (id_user_commit)
          ```
        
        - Then adding the changes: `git add .`  or `git add my_files`
        - And committing the changes: `git commit -m "Resolve conflicts"`
- I push *my_branch* into the repository, to update it : `git push origin my_branch`
- In GitLab, I create a new merge request from *my_branch* to *develop*, **AND:**
    - **I assign it to someone**
    - I choose the current Sprint as the *milestone*.


# 5 - Rebase

I can do a rebase when my branch is too late compared to *develop*.
With a rebase, I will move the origin of *my_branch* at the end of the branch *develop*.
It is a cleaner way than the merge to update a late branch, because it will respect the chronology and also not commit a `Merge 'develop' into 'my_branch'`

More precisely, the rebase will:
- Undo my commits and save them in a temporary space.
- Adding the commits from *develop* into *my_branch*.
- Redo my commits in *my_branch*.


In order to do it, I can follow those steps:
- I update my local branch *develop*: 
    - `git checkout develop`
    - `git pull origin develop`
- I switch to *my_branch*: `git checkout my_branch`
- I do the rebase: `git rebase develop`
- If there are conflicts:
    - I can abort the rebase: `git rebase --abort`
    - I can continue the rebase by:
        - Resolving the conflicts in the code.
        - Adding the changes: `git add .` or `git add my_files`.
        - Make the rebase continue: `git rebase --continue`


Links for more explanations:
- https://www.atlassian.com/git/tutorials/merging-vs-rebasing
- https://derekgourlay.com/blog/git-when-to-merge-vs-when-to-rebase/

# 6 - Useful Git Lines

- `git branch -a`: list all local and distant branches (a star is next to the curren working branch).
- `git checkout my_branch`: change the current working branch for *my_branche*.
- `git checkout -b new_branch`: create a new branch named *new_branch* and make it the current working branch.
- `git add .`: add all changed files to the staged changes.
- `git add myfile1 myfile2 myfile3`: add the listed changed files to the staged changes.
- `git commit -m "my message"`:  commit the staged changes with the message *"my message" for the commit.
- `git log --graph`: visualise the graph of the branches and the commits of the project with name commiter and commit message.
- 
