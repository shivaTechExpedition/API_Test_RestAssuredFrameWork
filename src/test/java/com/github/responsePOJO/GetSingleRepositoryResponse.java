package com.github.responsePOJO;

import java.sql.Date;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public @Data class GetSingleRepositoryResponse{
	 public int id;
	 public String node_id;
	 public String name;
	 @JsonProperty(value="full_name")
	 public String full_name;
	 @JsonProperty("private") 
	 public boolean myprivate;
	 public RepoOwner owner;
	 public String html_url;
	 public Object description;
	 public boolean fork;
	 public String url;
	 public String forks_url;
	 public String keys_url;
	 public String collaborators_url;
	 public String teams_url;
	 public String hooks_url;
	 public String issue_events_url;
	 public String events_url;
	 public String assignees_url;
	 public String branches_url;
	 public String tags_url;
	 public String blobs_url;
	 public String git_tags_url;
	 public String git_refs_url;
	 public String trees_url;
	 public String statuses_url;
	 public String languages_url;
	 public String stargazers_url;
	 public String contributors_url;
	 public String subscribers_url;
	 public String subscription_url;
	 public String commits_url;
	 public String git_commits_url;
	 public String comments_url;
	 public String issue_comment_url;
	 public String contents_url;
	 public String compare_url;
	 public String merges_url;
	 public String archive_url;
	 public String downloads_url;
	 public String issues_url;
	 public String pulls_url;
	 public String milestones_url;
	 public String notifications_url;
	 public String labels_url;
	 public String releases_url;
	 public String deployments_url;
	 public Date created_at;
	 public Date updated_at;
	 public Date pushed_at;
	 public String git_url;
	 public String ssh_url;
	 public String clone_url;
	 public String svn_url;
	 public Object homepage;
	 public int size;
	 public int stargazers_count;
	 public int watchers_count;
	 public Object language;
	 public boolean has_issues;
	 public boolean has_projects;
	 public boolean has_downloads;
	 public boolean has_wiki;
	 public boolean has_pages;
	 public boolean has_discussions;
	 public int forks_count;
	 public Object mirror_url;
	 public boolean archived;
	 public boolean disabled;
	 public int open_issues_count;
	 public Object license;
	 public boolean allow_forking;
	 public boolean is_template;
	 public boolean web_commit_signoff_required;
	 public ArrayList<Object> topics;
	 public String visibility;
	 public int forks;
	 public int open_issues;
	 public int watchers;
	 public String default_branch;
	 public RepoPermissions permissions;
	 public boolean allow_squash_merge;
	 public boolean allow_merge_commit;
	 public boolean allow_rebase_merge;
	 public boolean allow_auto_merge;
	 public boolean delete_branch_on_merge;
	 public boolean allow_update_branch;
	 public boolean use_squash_pr_title_as_default;
	 public String squash_merge_commit_message;
	 public String squash_merge_commit_title;
	 public String merge_commit_message;
	 public String merge_commit_title;
	 public int network_count;
	 public int subscribers_count;
}




