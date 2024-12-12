package com.example.examen_recycler_joseramon

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.examen_recycler_joseramon.adaptador.MontesAdapter
import com.example.examen_recycler_joseramon.databinding.ActivityMainBinding
import com.google.gson.Gson
import java.io.InputStream
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var montesMutableList: MutableList<Montes>
    private lateinit var copyList: MutableList<Montes>
    private lateinit var adapter: MontesAdapter

    private val getResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data
                val id = data?.getIntExtra("id", 0)
                val nombre = data?.getStringExtra("nombre")
                val continente = data?.getStringExtra("continente")
                val altura = data?.getStringExtra("altura")
                val url = data?.getStringExtra("url")
                val urlINFO = data?.getStringExtra("urlinfo")


                val nuevoMonte = Montes(id ?: 0 ,nombre ?: "", continente ?: "", altura ?: "",url ?: "", urlINFO?:"")


                montesMutableList.add(nuevoMonte)


                adapter.notifyItemInserted(montesMutableList.size - 1)
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initRecyclerView()



        binding.svMontes.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    val filteredList: MutableList<Montes> =
                        copyList.filter { it.nombre.lowercase().contains(newText) }
                            .toMutableList()

                    adapter.filterByName(filteredList)
                }
                return false
            }
        })

    }

    private fun initRecyclerView() {
        montesMutableList = getListFromJson().toMutableList()
        adapter = MontesAdapter(
            montesList = montesMutableList,
            onClickListener = { montes -> onItemSelected(montes) },
            onClickDelete = { position -> onDeleteItem(position) },

            )

        val manager = LinearLayoutManager(this)

        val decoration = DividerItemDecoration(this, manager.orientation)

        binding.rvMontes.layoutManager = manager
        binding.rvMontes.adapter = adapter

        binding.rvMontes.addItemDecoration(decoration)
        binding.btnPedir.setOnClickListener { IrPedirInfo() }
    }

    private fun onDeleteItem(position: Int) {
        montesMutableList.removeAt(position)
        adapter.notifyItemRemoved(position)
    }

    private fun IrPedirInfo() {
        val intent = Intent(this, ActivityAnadirMonte::class.java)
        getResult.launch(intent)
    }

    private fun onItemSelected(item: Montes) {

    }

    fun getListFromJson(): MutableList<Montes> {
        val json: String? = getJsonFromAssets(this, "montes.json")
        val montesList = Gson().fromJson(json, Array<Montes>::class.java).toMutableList()

        copyList = montesList.toMutableList()

        return montesList
    }
    fun getJsonFromAssets(context: Context, file: String): String? {
        var json=""
        val stream: InputStream =context.assets.open(file)
        val size: Int = stream.available()
        val buffer = ByteArray(size)
        stream.read(buffer)
        stream.close()

        json = String(buffer, Charset.defaultCharset())
        return json
    }

}
