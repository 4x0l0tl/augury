package com.augury.core.repository;

import org.joda.time.DateTime;

import com.augury.model.Author;
import com.augury.model.Branch;
import com.augury.model.Change;
import com.augury.model.Commit;
import com.augury.model.Directory;
import com.augury.model.DirectoryNode;
import com.augury.model.Issue;
import com.augury.model.Project;

public interface RelationshipRepository {
	void has(Project project, Branch branch); 
	void authored(Author author, Commit commit);
	void branched(Branch parent, Branch child,DateTime dateBranched);
	void committedAt(Commit commit);
	void committedTo(Commit commit, Branch branch);
	void previous(Commit commit, Commit lastestCommit, Branch branch);
	void relatingTo(Commit commit,Issue issue);
	void affected(Commit commit, DirectoryNode node, Change change);
	void contains(Directory directory, DirectoryNode node);
}
