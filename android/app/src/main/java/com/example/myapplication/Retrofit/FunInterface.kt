package com.example.myapplication.Retrofit

import com.example.myapplication.dto.Category
import com.example.myapplication.dto.Project
import com.example.myapplication.dto.User
import com.example.myapplication.retrofitPacket.FavoritePacket
import com.example.myapplication.retrofitPacket.HomeInitPacket
import com.example.myapplication.retrofitPacket.LoginCheckPacket
import com.example.myapplication.retrofitPacket.ProjectDetail
import com.example.myapplication.retrofitPacket.SupportPacket
import com.example.myapplication.retrofitPacket.UserPacket
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FunInterface {
    @GET("/")
    fun getHomeInitData():Call<HomeInitPacket>
    @GET("/homeScroll")
    fun getScrollProject(@Body pageNum:Int):Call<List<ProjectDetail>>

    @POST("/login")
    fun tryLogin(@Body loginCheckPacket: LoginCheckPacket) : Call<Boolean>

    @POST("/signIn")
    fun signIn(@Body user:UserPacket) : Call<Boolean>

    @GET("/user/{userId}")
    fun getUser(@Path("userId") userId: String) : Call<UserPacket>

    @POST("/logOut")
    fun logOut(@Body user: UserPacket) : Call<Void>

    /* ----------------------------------------*/

    // 상세 보기용
    @GET("/project/{projectId}")
    fun getProjectDetail(@Path("projectId") projectId: Int) :Call<ProjectDetail>

    @GET("/project/list")
    fun getProjectList() :Call<List<ProjectDetail>>


    // 프로젝트 인기순
    @GET("/project/list/ranking")
    fun getProjectRankingList() : Call<List<ProjectDetail>>

    @GET("/project/deadline")
    fun getProjectDeadline() : Call<List<ProjectDetail>>



    // 검색 Key로 시작하는 title을 가진 프로젝트 리스트 (10 ~ 20)?
    @GET("/project/search")
    fun getProjectSearch(@Body searchKey:String) : Call<List<ProjectDetail>>


    //프로젝트 작성
    @POST("/project/write")
    fun writeProject(@Body project: ProjectDetail) :Call<Void>


    /*-----------------------------------------*/

    // 프로젝트에 좋아요 누른 유저 수
    @GET("/favorite/user")
    fun getFavoriteUserCount(@Body projectId:Int) : Call<Int>

    // 자신이 좋아요 누른 프로젝트 리스트
    @GET("/favorite/project")
    fun getFavoriteProject(@Body userId:Int) : Call<List<Project>>

    @POST("/favorite")
    fun createFavorite(@Body favoritePacket: FavoritePacket) :Call<Void>

    //FavoriteDelPacket => projectId + userId
    @DELETE("/favorite/delete")
    fun deleteFavorite(@Body favoritePacket: FavoritePacket) : Call<Void>

    /*-----------------------------------------*/

    // 후원한 유저 수
    @GET("/support/user")
    fun getSupportUserCount(@Body projectId: Int) : Call<Int>

    // 자신이 후원한 프로젝트 리스트
    @GET("/support/project")
    fun getSupportingProject(@Body userId:Int) : Call<List<Project>>

    @POST("/support")
    fun createSupport(@Body supportPacket: SupportPacket) : Call<Void>

    //SupportDelPacket => projectId + userId
    @DELETE("/support/delete")
    fun getSupportDelete(@Body supportPacket: SupportPacket) : Call<Void>

    /*-----------------------------------------*/

    @GET("/category/{categoryId}")
    fun getCategory(@Path("categoryId") categoryId:Int) : Call<Category>
}