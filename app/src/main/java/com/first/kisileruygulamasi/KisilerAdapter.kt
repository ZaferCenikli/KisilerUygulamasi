package com.first.kisileruygulamasi

import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class KisilerAdapter(private val mContext:Context,private var kisilerListe:List<Kisiler>
,private val vt:VeriTaban)
    :RecyclerView.Adapter<KisilerAdapter.kartasarimTutucu>() {


    inner class kartasarimTutucu(tasarim:View):RecyclerView.ViewHolder(tasarim){

        var imageView:ImageView
        var textView:TextView

        init {
             textView=tasarim.findViewById(R.id.textView)
             imageView=tasarim.findViewById(R.id.imageView)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): kartasarimTutucu {
        val tasarim=LayoutInflater.from(mContext).inflate(R.layout.kisi_card_tasarim,parent,false)
        return kartasarimTutucu(tasarim)

    }

    override fun getItemCount(): Int {
        return kisilerListe.size
    }

    override fun onBindViewHolder(holder: kartasarimTutucu, position: Int) {
        val kisi=kisilerListe.get(position)
        holder.textView.text="${kisi.kisi_ad}-${kisi.kisi_tel}"
        holder.imageView.setOnClickListener {
            val popupMenu=PopupMenu(mContext,holder.imageView)
            popupMenu.menuInflater.inflate(R.menu.popup_menu,popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {
                menuItem->
                when(menuItem.itemId){
                    R.id.action_sil ->{
                        Snackbar.make(holder.imageView,"${kisi.kisi_ad} Silinsin mi?",Snackbar.LENGTH_LONG)
                            .setAction("EVET"){}.show()
                        KisilerDao().kisiSil(vt,kisi.Kisi_id)
                        kisilerListe=KisilerDao().tumKisiler(vt)
                        notifyDataSetChanged()
                        true
                    }
                    R.id.action_guncelle ->{
                        alertGuncelle(kisi)
                        true
                    }
                    else ->false
                }
            }
            popupMenu.show()
        }
    }
    fun alertGuncelle(kisi:Kisiler){
        val tasarim=LayoutInflater.from(mContext).inflate(R.layout.alert_tasarim,null)
        val edittext=tasarim.findViewById(R.id.editTextTextisim) as EditText
        val edittexttel=tasarim.findViewById(R.id.editTextTextTelNo) as EditText

        edittext.setText(kisi.kisi_ad)
        edittexttel.setText(kisi.kisi_tel)

        val ad= AlertDialog.Builder(mContext)
        ad.setTitle("Kisi Güncelle")
        ad.setView(tasarim)
        ad.setPositiveButton("Güncelle"){DialogInterface,i->
            val kişiad=edittext.text.toString().trim()
            val kisitel=edittexttel.text.toString().trim()
            KisilerDao().kisiGuncelle(vt,kisi.Kisi_id,"$kişiad","$kisitel")
            kisilerListe=KisilerDao().tumKisiler(vt)
            notifyDataSetChanged()

            Toast.makeText(mContext,"$kişiad-$kisitel Güncellendi", Toast.LENGTH_SHORT).show()
        }
        ad.setNegativeButton("İptal"){DialogInterface,i->
        }
        ad.create().show()
    }
}