package viper.sample.model.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rx.Observable
import viper.sample.model.entities.Branch
import viper.sample.model.entities.Commit
import viper.sample.model.entities.Repo

/**
 *
 * Created by Nick Cipollo on 1/4/17.
 */
interface GitHubAPI {
    @GET("users/{user}/repos")
    fun listRepos(@Path("user") user: String): Observable<List<Repo>>

    @GET("repos/{user}/{repo}/commits")
    fun listCommits(@Path("user") user: String,
                    @Path("repo") repoName: String,
                    @Query("sha") branch: String): Observable<List<Commit>>

    @GET("repos/{user}/{repo}/branches")
    fun listBranches(@Path("user") user: String,
                     @Path("repo") repoName: String): Observable<List<Branch>>
}