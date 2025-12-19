package com.vocabapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.vocabapp.data.dao.MeaningDao
import com.vocabapp.data.dao.VocabListDao
import com.vocabapp.data.dao.WordDao
import com.vocabapp.data.entities.Meaning
import com.vocabapp.data.entities.VocabList
import com.vocabapp.data.entities.Word
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [VocabList::class, Word::class, Meaning::class],
    version = 1,
    exportSchema = false
)
abstract class VocabDatabase : RoomDatabase() {
    
    abstract fun vocabListDao(): VocabListDao
    abstract fun wordDao(): WordDao
    abstract fun meaningDao(): MeaningDao
    
    companion object {
        @Volatile
        private var INSTANCE: VocabDatabase? = null
        
        fun getDatabase(context: Context): VocabDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VocabDatabase::class.java,
                    "vocab_database"
                )
                    .addCallback(DatabaseCallback())
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
    
    private class DatabaseCallback : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                CoroutineScope(Dispatchers.IO).launch {
                    populateSampleData(database)
                }
            }
        }
        
        private suspend fun populateSampleData(database: VocabDatabase) {
            val vocabListDao = database.vocabListDao()
            val wordDao = database.wordDao()
            val meaningDao = database.meaningDao()
            
            // Create TOEFL vocabulary list
            val toeflId = vocabListDao.insertList(
                VocabList(name = "TOEFL Essential", description = "Essential vocabulary for TOEFL exam")
            )
            
            // Add TOEFL words
            val toeflWords = listOf(
                Triple("abandon", "/əˈbændən/", listOf(
                    "verb" to "to leave completely and finally",
                    "verb" to "to give up control of something"
                )),
                Triple("abstract", "/ˈæbstrækt/", listOf(
                    "adjective" to "existing in thought or as an idea",
                    "noun" to "a summary of a text or speech"
                )),
                Triple("abundant", "/əˈbʌndənt/", listOf(
                    "adjective" to "existing in large quantities; plentiful"
                )),
                Triple("academic", "/ˌækəˈdemɪk/", listOf(
                    "adjective" to "relating to education and scholarship",
                    "noun" to "a teacher or scholar in a university"
                )),
                Triple("accelerate", "/əkˈseləreɪt/", listOf(
                    "verb" to "to increase in speed",
                    "verb" to "to cause to happen sooner"
                ))
            )
            
            for ((word, phonetic, meanings) in toeflWords) {
                val wordId = wordDao.insertWord(Word(listId = toeflId, word = word, phonetic = phonetic))
                for ((pos, def) in meanings) {
                    meaningDao.insertMeaning(Meaning(wordId = wordId, partOfSpeech = pos, definition = def))
                }
            }
            
            // Create GRE vocabulary list
            val greId = vocabListDao.insertList(
                VocabList(name = "GRE Advanced", description = "Advanced vocabulary for GRE exam")
            )
            
            // Add GRE words
            val greWords = listOf(
                Triple("aberration", "/ˌæbəˈreɪʃən/", listOf(
                    "noun" to "a departure from what is normal or expected",
                    "noun" to "a mental or moral deviation"
                )),
                Triple("abeyance", "/əˈbeɪəns/", listOf(
                    "noun" to "a state of temporary disuse or suspension"
                )),
                Triple("abjure", "/əbˈdʒʊr/", listOf(
                    "verb" to "to solemnly renounce a belief or claim"
                )),
                Triple("abnegate", "/ˈæbnɪɡeɪt/", listOf(
                    "verb" to "to renounce or reject something desired",
                    "verb" to "to deny oneself"
                )),
                Triple("abscond", "/əbˈskɒnd/", listOf(
                    "verb" to "to leave hurriedly and secretly to escape detection"
                ))
            )
            
            for ((word, phonetic, meanings) in greWords) {
                val wordId = wordDao.insertWord(Word(listId = greId, word = word, phonetic = phonetic))
                for ((pos, def) in meanings) {
                    meaningDao.insertMeaning(Meaning(wordId = wordId, partOfSpeech = pos, definition = def))
                }
            }
            
            // Create The Old Man and the Sea vocabulary list
            val oldManId = vocabListDao.insertList(
                VocabList(name = "The Old Man and the Sea", description = "Vocabulary from Hemingway's novel")
            )
            
            // Add literary words
            val literaryWords = listOf(
                Triple("skiff", "/skɪf/", listOf(
                    "noun" to "a small light boat for rowing or sailing"
                )),
                Triple("gaff", "/ɡæf/", listOf(
                    "noun" to "a hook used for landing large fish"
                )),
                Triple("phosphorescence", "/ˌfɒsfəˈresəns/", listOf(
                    "noun" to "light emitted without combustion or heat"
                )),
                Triple("benevolent", "/bəˈnevələnt/", listOf(
                    "adjective" to "well-meaning and kindly"
                )),
                Triple("resolution", "/ˌrezəˈluːʃən/", listOf(
                    "noun" to "a firm decision to do or not do something",
                    "noun" to "the quality of being determined"
                ))
            )
            
            for ((word, phonetic, meanings) in literaryWords) {
                val wordId = wordDao.insertWord(Word(listId = oldManId, word = word, phonetic = phonetic))
                for ((pos, def) in meanings) {
                    meaningDao.insertMeaning(Meaning(wordId = wordId, partOfSpeech = pos, definition = def))
                }
            }
        }
    }
}

