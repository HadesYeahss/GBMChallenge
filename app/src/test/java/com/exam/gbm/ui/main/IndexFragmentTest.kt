package com.exam.gbm.ui.main


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.exam.gbm.remote.api.IndexApiService
import com.exam.gbm.remote.responses.ResponseResult
import com.exam.gbm.repository.IndexRepository
import com.exam.gbm.ui.main.viewmodel.IpcViewModel
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import util.MockResponseFileReader
import javax.inject.Inject

@RunWith(JUnit4::class)
class IndexFragmentTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Inject
    private lateinit var repository: IndexRepository

    private val server = MockWebServer()

    private lateinit var ipcViewModel: IpcViewModel
    private lateinit var mockedResponse: String


    @Before
    fun init() {
        server.start(8000)
        val okHttpClient = OkHttpClient
            .Builder()
            .build()
        val service = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(server.url("/").toString())
            .client(okHttpClient)
            .build().create(IndexApiService::class.java)
        repository = IndexRepository(service)
        ipcViewModel = IpcViewModel(repository)
    }


    @Test
    fun testSuccess() {
        mockedResponse = MockResponseFileReader("mockDetailsApi/success.json").content
        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(mockedResponse)
        )
        runBlocking {
            ipcViewModel.indexRepository.getIndex("cc4c350b-1f11-42a0-a1aa-f8593eafeb1e").let {
                it.collect { response ->
                    assertTrue(response is ResponseResult.Success)
                }
            }
        }
    }


    @Test
    fun testError() {
        mockedResponse = MockResponseFileReader("mockDetailsApi/success.json").content
        server.enqueue(
            MockResponse()
                .setResponseCode(404)
                .setBody(mockedResponse)
        )
        runBlocking {
            ipcViewModel.indexRepository.getIndex("cc4c350b-1f11-42a0-a1aa-f8593eafeb1e").let {
                it.collect { response ->
                    assertTrue(response is ResponseResult.Failure)
                }
            }
        }
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

}