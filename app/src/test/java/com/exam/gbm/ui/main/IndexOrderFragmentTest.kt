package com.exam.gbm.ui.main


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.exam.gbm.remote.api.IndexApiService
import com.exam.gbm.remote.responses.ResponseResult
import com.exam.gbm.repository.IndexRepository
import com.exam.gbm.ui.main.viewmodel.IpcListViewModel
import com.exam.gbm.utils.Constants
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
class IndexOrderFragmentTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Inject
    private lateinit var repository: IndexRepository

    private val server = MockWebServer()

    private lateinit var ipcViewModel: IpcListViewModel
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
        ipcViewModel = IpcListViewModel(repository)
    }


    @Test
    fun testSuccess() {
        mockedResponse = MockResponseFileReader("mockDetailsApi/successList.json").content
        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(mockedResponse)
        )
        runBlocking {
            ipcViewModel.indexRepository.getIndexList("test").let {
                it.collect { response ->
                    assertTrue(response is ResponseResult.Success)
                }
            }
        }
    }

    @Test
    fun testEmpty() {
        mockedResponse = MockResponseFileReader("mockDetailsApi/successListEmpty.json").content
        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(mockedResponse)
        )
        runBlocking {
            ipcViewModel.indexRepository.getIndexList("test").let {
                it.collect { response ->
                    when (response) {
                        is ResponseResult.Success -> {
                            response.data.let { list ->
                                assertTrue(list.isEmpty())
                            }
                        }
                    }
                }
            }
        }
    }


    @Test
    fun testError() {
        mockedResponse = MockResponseFileReader("mockDetailsApi/successList.json").content
        server.enqueue(
            MockResponse()
                .setResponseCode(404)
                .setBody(mockedResponse)
        )
        runBlocking {
            ipcViewModel.indexRepository.getIndexList("").let {
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