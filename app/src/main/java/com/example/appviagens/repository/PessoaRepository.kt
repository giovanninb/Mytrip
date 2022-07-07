package com.example.appviagens.repository

import android.app.Application
import androidx.room.Query
import com.example.appviagens.dao.Connection
import com.example.appviagens.dao.PessoaDao
import com.example.appviagens.model.Pessoa

class PessoaRepository(app: Application) {

    private val pessoaDao: PessoaDao

    init {
        pessoaDao = Connection
            .getDB(app).pessoaDao()
    }

    suspend fun save(pessoa: Pessoa) {
        if (pessoa.id == 0) {
            pessoaDao.insert(pessoa)
        }
        else {
            pessoaDao.update(pessoa)
        }
    }

    suspend fun findAll(): List<Pessoa> = pessoaDao.findAll()

    suspend fun findById(id: Int) = pessoaDao.findById(id)

    suspend fun delete(contato: Pessoa) = pessoaDao.delete(contato)

    suspend fun findByUsername(username: String, password: String) = pessoaDao.verificar(username, password)



}