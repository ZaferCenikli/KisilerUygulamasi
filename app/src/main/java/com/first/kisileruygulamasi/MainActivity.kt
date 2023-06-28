package com.first.kisileruygulamasi

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),SearchView.OnQueryTextListener {
    lateinit var kisilerListe:ArrayList<Kisiler>
    lateinit var adapter: KisilerAdapter
    lateinit var vt: VeriTaban
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.title="Kişiler Uygulamasi"
        setSupportActionBar(toolbar)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager=LinearLayoutManager(this)

        vt= VeriTaban(this)
        tumKisilerAl()



        floatingActionButton.setOnClickListener {
            alertGoster()

        }

    }
    fun alertGoster(){
        val tasarim=LayoutInflater.from(this@MainActivity).inflate(R.layout.alert_tasarim,null)
        val edittext=tasarim.findViewById(R.id.editTextTextisim) as EditText
        val edittexttel=tasarim.findViewById(R.id.editTextTextTelNo) as EditText

        val ad=AlertDialog.Builder(this@MainActivity)
        ad.setTitle("Kisi Ekle")
        ad.setView(tasarim)
        ad.setPositiveButton("Ekle"){DialogInterface,i->
            val kişiad=edittext.text.toString().trim()
            val kisitel=edittexttel.text.toString().trim()
            KisilerDao().kisiEkle(vt,kişiad,kisitel)
            tumKisilerAl()


            Toast.makeText(this,"$kişiad-$kisitel Eklendi",Toast.LENGTH_SHORT).show()
        }
        ad.setNegativeButton("İptal"){DialogInterface,i->
        }
        ad.create().show()
        }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu,menu)
        val item=menu?.findItem(R.id.action_Ara)
        val searchView=item?.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        aramaKelime(query)

        Log.e("Göderilen arama",query)
        return true

    }

    override fun onQueryTextChange(newText: String): Boolean {
        aramaKelime(newText)
        Log.e("Göderilen arama",newText)
        return true

    }
    fun tumKisilerAl(){
        kisilerListe=KisilerDao().tumKisiler(vt)
        adapter= KisilerAdapter(this@MainActivity,kisilerListe,vt)
        recyclerView.adapter=adapter

    }
    fun aramaKelime(aramaKelime:String){
        kisilerListe=KisilerDao().kisiAra(vt,aramaKelime)
        adapter= KisilerAdapter(this@MainActivity,kisilerListe,vt)
        recyclerView.adapter=adapter

    }


}