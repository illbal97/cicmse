import { FlatTreeControl, NestedTreeControl } from '@angular/cdk/tree';
import { Component, Inject, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatTreeFlatDataSource, MatTreeFlattener, MatTreeNestedDataSource } from '@angular/material/tree';
import { lastValueFrom, Observable, of, Subscription } from 'rxjs';
import { asanaProject } from 'src/app/model/asana/asana-project';
import { AsanaTask } from 'src/app/model/asana/asana-task';
import { GitlabBranchCreationData } from 'src/app/model/gitlab/gitlab-branch-creation';
import { GitlabCreationData } from 'src/app/model/gitlab/gitlab-creation-data';
import { User } from 'src/app/model/user.model';
import { GitlabHomeComponent } from 'src/app/pages/gitlab/gitlab-home/gitlab-home.component';
import { AsanaService } from 'src/app/services/asana/asana.service';
import { GitlabService } from 'src/app/services/gitlab/gitlab.service';

interface BranchNode {
  name: string;
  children?: BranchNode[];
}

const TREE_DATA: BranchNode[] = [
  {
    name: 'main',
    children: [
      {
        name: 'release',
        children: [{ name: 'develop' }],
      }
    ],
  },
];
interface ExampleFlatNode {
  expandable: boolean;
  name: string;
  level: number;
}

@Component({
  selector: 'app-project-creation-dialog',
  templateUrl: './project-creation-dialog.component.html',
  styleUrls: ['./project-creation-dialog.component.scss']
})
export class ProjectCreationDialogComponent implements OnInit, OnDestroy {

  creationGitlabProject: FormGroup;
  projectName: string = "";
  notExistName: boolean = false;
  disabledCheckbox: boolean = false;
  gitlabProject: any | undefined;
  gitlabBranchCreationData: GitlabBranchCreationData = new GitlabBranchCreationData();
  project: asanaProject | undefined;
  workspaces: Array<any> = [];
  projects: Array<any> = [];
  tasks: Array<AsanaTask> = [];
  selectedWorkspaceGid: String = "";
  selectedProjectGid: String = "";
  subscriptionWorkspaces: Subscription | undefined;
  subscriptionUser: Subscription | undefined;
  asanaStatus: String = "";
  isLoading: boolean = true;

  private _transformer = (node: BranchNode, level: number) => {
    return {
      expandable: !!node.children && node.children.length > 0,
      name: node.name,
      level: level,
    };
  };

  treeControl = new FlatTreeControl<ExampleFlatNode>(
    node => node.level,
    node => node.expandable,
  );

  treeFlattener = new MatTreeFlattener(
    this._transformer,
    node => node.level,
    node => node.expandable,
    node => node.children,
  );

  dataSource = new MatTreeFlatDataSource(this.treeControl, this.treeFlattener);


  constructor(
    private gitlabService: GitlabService,
    private asanaService: AsanaService,
    private formBuilder: FormBuilder,
    public dialogRef: MatDialogRef<GitlabHomeComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { user: User, gitlab_projects: any[] }
  ) {
    this.dataSource.data = TREE_DATA;
    this.creationGitlabProject = formBuilder.group({
      name: '',
      path: '',
      visibility: '',
      description: '',
      tasks: formBuilder.array([])
    })
  }
  ngOnDestroy(): void {
    this.subscriptionUser?.unsubscribe();
    this.subscriptionWorkspaces?.unsubscribe();

  }
  hasChild = (_: number, node: ExampleFlatNode) => node.expandable;

  ngOnInit(): void {
    this.subscriptionWorkspaces = this.asanaService.getAsanaWorkspaces(this.data.user).subscribe({
      next: (asanaWorkspace: any) => {
        this.workspaces = asanaWorkspace;
      },
      error: (err: string) => {
        console.error(err);

      },
      complete: () => { }
    });
  }

  async createGitlabProject() {
    let gitlab_data = new GitlabCreationData();
    gitlab_data.name = this.creationGitlabProject.value.name;
    gitlab_data.visibility = this.creationGitlabProject.value.visibility;
    gitlab_data.description = this.creationGitlabProject.value.description;
    gitlab_data.default_branch = 'main';
    gitlab_data.initialize_with_readme = 'true';
    let features_branch = this.taskFormArray.value;

    await lastValueFrom(this.gitlabService.createProject(this.data.user, gitlab_data)).then(p => {
      this.gitlabProject = p;
    }).catch(err => {
      console.log(err);
    });

    await lastValueFrom(this.gitlabService.createBranch(this.data.user, this.gitlabProject.id,
      this.setGitlabBranchCreationData("release", gitlab_data.default_branch))).then(b => {
        console.log(b)
      }).catch(err => {
        console.log(err);
      });

    await lastValueFrom(this.gitlabService.createBranch(this.data.user, this.gitlabProject.id,
      this.setGitlabBranchCreationData("develop", "release"))).then(b => {
        console.log(b)
      }).catch(err => {
        console.log(err);
      });

    for (var name of features_branch) {
      await lastValueFrom(this.gitlabService.createBranch(this.data.user, this.gitlabProject.id,
        this.setGitlabBranchCreationData(this.formatAsanaTaskToBranch(name), "develop"))).then(b => {
          console.log(b)
        }).catch(err => {
          console.log(err);
        });

    }


    this.dialogRef.close(this.gitlabProject);

  }

  checkProjectNameIsUnique(event: any) {
    if(this.data.gitlab_projects.length !== 0) {
      for(var project of this.data.gitlab_projects) {
        if(project.name == event) {
          this.notExistName = true;
          break;
        }else {
          this.notExistName = false;
        }
      }

  }
  }

  setGitlabBranchCreationData(branch: String, ref: String): GitlabBranchCreationData {
    this.gitlabBranchCreationData.branch = branch;
    this.gitlabBranchCreationData.ref = ref;
    return this.gitlabBranchCreationData;
  }

  loadAsanaProjects(gid: any, isImmideatly = false) {
    this.asanaService.getAsanaProjectbyWorkspace(this.data.user, gid, isImmideatly).subscribe(p => {
      this.projects = p;
    });
    this.tasks = [];
    this.selectedProjectGid = ''
    this.taskFormArray.clear();
  }

   loadAsanaTask(gid: any) {
    this.isLoading = false;
    //this.tasks = lastValueFrom(this.asanaService.getAsanaTaskByProject(this.data.user, gid));
    this.asanaService.getAsanaTaskByProject(this.data.user, gid).subscribe({
      next: (t) => {
        this.tasks = t;
        console.log(this.tasks)
      },
      error: (error) => {
        console.log(error)
      },
      complete: () => {
        this.isLoading = true;
      }

    });

    this.taskFormArray.clear();

  }

  onChange(name: any, isChecked: boolean, event: any) {

    console.log( this.taskFormArray.controls)
    if (isChecked) {
      this.taskFormArray.push(new FormControl(name));
    } else {
      const index = this.taskFormArray.controls.findIndex((x: any) => x.value === name);
      this.taskFormArray.removeAt(index);
      ;
    }

  }

  ngAfterViewInit() {
    this.treeControl.expandAll();
  }

  get taskFormArray() {
    return this.creationGitlabProject.controls['tasks'] as FormArray;
  }

  formatAsanaTaskToBranch(name: String): String {
    return name.replace(' ', '_').toLowerCase();
  }




}
