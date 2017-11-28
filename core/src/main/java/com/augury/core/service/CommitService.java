package com.augury.core.service;

import com.augury.model.Branch;
import com.augury.model.Commit;

public interface CommitService extends Service<Commit>{
	Commit getLatestCommitForBranch(Branch branch);

	Commit getCommitByRevision(String revision);
}
