package com.example.appviagens.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.appviagens.model.Viagem

@Dao
interface ViagemDao {

    @Insert()
    suspend fun insert(viagem: Viagem)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(viagem: Viagem)

    @Query("Select * from Viagem v where v.usuarioID = :userID")
    fun getViagensByUser(userID: Int): LiveData<List<Viagem>>

    @Query("Select * from Viagem v where v.id = :id")
    suspend fun findById(id: Int): Viagem

    @Query("Delete from Viagem where Viagem.id = :id")
    suspend fun deleteByID(id: Int)

    @Query("select destino from Viagem where Viagem.id = :id")
    fun getDestinoByViagem(id: Int): String

    @Query("SELECT COALESCE(SUM(valor),0) FROM Despesa as d inner join viagem v on d.viagemID = v.id where v.id = :id")
    fun somaDespesasByViagem(id: Int):  LiveData<Double>

//
//    @Query("select id from Pessoa c where c.login = :login and c.senha = :senha")
//    fun login(login: String, senha: String): Int?

}