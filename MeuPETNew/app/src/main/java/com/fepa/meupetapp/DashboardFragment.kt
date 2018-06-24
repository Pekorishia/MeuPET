    package com.fepa.meupetapp


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.os.AsyncTask.execute
import android.os.AsyncTask
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.fragment_dashboard.*


    // TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DashboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class DashboardFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        /*bt_connect.setOnClickListener({
            val asyncTask = MeuAsyncTask()
            asyncTask.execute()
        })*/
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DashboardFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
                DashboardFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }

    internal inner class MeuAsyncTask : AsyncTask<Void, Void, String>() {

        private val message: String? = null

        override fun doInBackground(vararg params: Void): String {

            // Instantiate the RequestQueue.
            val queue = Volley.newRequestQueue(getActivity()!!.getApplicationContext())
            val url = "http://google.com"

            // Request a string response from the provided URL.
            val stringRequest = StringRequest(Request.Method.GET, url,
                    object : Response.Listener<String> {
                        override fun onResponse(response: String) {
                            // Display the first 500 characters of the response string.
                            et_message.setText("Response is: " + response.substring(0, 500))
                        }
                    }, object : Response.ErrorListener {
                override fun onErrorResponse(error: VolleyError) {
                    et_message.setText("That didn't work!")
                }
            })

            // Add the request to the RequestQueue.
            queue.add(stringRequest)
            return ""
        }

        override fun onPostExecute(result: String) {

            //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            // tv_data.setText(result);
        }
    }
}
