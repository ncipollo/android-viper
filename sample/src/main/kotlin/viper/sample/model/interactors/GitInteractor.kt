package viper.sample.model.interactors

import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import viper.sample.model.api.GitHubAPI
import viper.sample.model.entities.Branch
import viper.sample.model.entities.Commit
import viper.sample.model.entities.Repo
import javax.inject.Inject
import javax.inject.Singleton

/**
 * This interactor manages the git use cases. It provides the following functionality:
 * - Fetches repositories for a user
 * - Fetches branches for a repository.
 * - Fetches commits for a repository and branch. Commit fetches may be paged.
 *
 * Created by Nick Cipollo on 1/3/17.
 */
@Singleton
class GitInteractor @Inject constructor(val api: GitHubAPI) {
    fun fetchRepos(user: String): Observable<Repo> {
        return api.listRepos(user)
                .flatMap { Observable.from(it) }
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun fetchBranches(user: String, repo: Repo): Observable<Branch> {
        return api.listBranches(user,repo.name)
                .flatMap { Observable.from(it) }
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun fetchCommits(user: String, repo: Repo, branch: Branch): Observable<Commit> {
        return api.listCommits(user,repo.name,branch.name)
                .flatMap { Observable.from(it) }
                .observeOn(AndroidSchedulers.mainThread())
    }
}