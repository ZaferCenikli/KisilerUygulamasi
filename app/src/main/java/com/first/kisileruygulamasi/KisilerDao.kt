package com.first.kisileruygulamasi

import android.annotation.SuppressLint
import android.content.ContentValues

class KisilerDao {
    fun kisiSil(vt:VeriTaban,kisi_id:Int){
        val db=vt.readableDatabase
        db.delete("kisiler","kisi_id=?", arrayOf(kisi_id.toString()))
        db.close()
    }
    fun kisiEkle(vt: VeriTaban,kisi_ad:String,kisi_tel:String){
        val db=vt.readableDatabase
        val values=ContentValues()
        values.put("kisi_ad",kisi_ad)
        values.put("kisi_tel",kisi_tel)
        db.insertOrThrow("kisiler",null,values)
        db.close()

    }
    fun kisiGuncelle(vt: VeriTaban,kisi_id: Int,kisi_ad:String,kisi_tel:String){
        val db=vt.readableDatabase
        val values=ContentValues()
        values.put("kisi_ad",kisi_ad)
        values.put("kisi_tel",kisi_tel)
        db.update("kisiler",values,"kisi_id=?", arrayOf(kisi_id.toString()))
        db.close()

    }

    @SuppressLint("Range")
    fun tumKisiler(vt:VeriTaban):ArrayList<Kisiler>{
        val db=vt.readableDatabase
        val asd=ArrayList<Kisiler>()
        val c=db.rawQuery("SELECT * FROM kisiler",null)


        while (c.moveToNext()){
                val kisi = Kisiler(c.getInt(c.getColumnIndex("kisi_id"))
                    ,c.getString(c.getColumnIndex("kisi_ad"))
                    ,c.getString(c.getColumnIndex("kisi_tel")))
            asd.add(kisi)
        }
        return asd
    }
    @SuppressLint("Range")
    fun kisiAra(vt: VeriTaban,aramaKelime:String):ArrayList<Kisiler>{
        val db=vt.readableDatabase
        val asd=ArrayList<Kisiler>()
        val c=db.rawQuery("SELECT * FROM kisiler WHERE kisi_ad like '%$aramaKelime%'",null)

        while (c.moveToNext()){
            val kisi = Kisiler(c.getInt(c.getColumnIndex("kisi_id"))
                ,c.getString(c.getColumnIndex("kisi_ad"))
                ,c.getString(c.getColumnIndex("kisi_tel")))
            asd.add(kisi)
        }
        return asd
    }
}