package com.example.loginretrofit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.loginretrofit.Common.Common
import com.example.loginretrofit.Model.APIResponse
import com.example.loginretrofit.Remote.IMuAPI
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    internal lateinit var mService: IMuAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mService = Common.api

        btn_login.setOnClickListener {
            autenticarUsuario(edtUser.text.toString(), edtPass.text.toString())
        }
    }

    private fun autenticarUsuario(email: String, password: String) {
        mService.login(email, password)
            .enqueue(object : Callback<APIResponse> {
                override fun onFailure(call: Call<APIResponse>, t: Throwable) {
                    Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<APIResponse>, response: Response<APIResponse>) {
                    if (response.body()!!.isError) {
                        Toast.makeText(
                            this@MainActivity,
                            response.body()!!.error_msg,
                            Toast.LENGTH_SHORT
                        ).show()

                    } else {
                        val intent = Intent(this@MainActivity, Aplicacion::class.java)
                        startActivity(intent)
                    }
                }

            })

    }
}
