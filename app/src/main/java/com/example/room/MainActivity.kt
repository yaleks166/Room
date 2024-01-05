package com.example.room


import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.room.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val adapter2 = PlantAdapter()
    private var index = 0
    private val imageIdList = listOf(
        R.drawable.plant1,
        R.drawable.plant2,
        R.drawable.plant3,
        R.drawable.plant4,
        R.drawable.plant5,
    )

//    @RequiresApi(Build.VERSION_CODES.N) // для андроид выше 7.0 (API 24)
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()


//        val spinner: Spinner = binding.spinner2
        val adapter = ArrayAdapter<Item>(this, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter  //Применяем адаптер к спиннеру

        val adapter2 = ArrayAdapter<String>(this, android.R.layout.simple_gallery_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner2.adapter = adapter2  //Применяем адаптер к спиннеру


////    Создайте адаптер массива
//    val adapter = ArrayAdapter.createFromResource(this,
//    R.array.city_list, android.R.layout.simple_spinner_item)
////    Укажите макет, который будет использоваться при появлении списка вариантов.
//    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val db = MainDb.getDb(this) // создаем переменную и присваиваем ей значение
    db.getDao().getAllItem().asLiveData().observe(this){ list->
        binding.textView.text = "" // Очищаем список заполнения

        list.forEach { // Заполняем список данными
//          val text = "Id:  Name: ${it.name} Price: ${it.price}\n" //Шаблон данных и перевод строки
            val text = "${it.id} ${it.name} ${it.price}\n" //Шаблон данных и перевод строки
            binding.textView.append(text)
            val mas = listOf(text).joinToString(",")
            adapter2.add(mas).toString()
        }
    }

        val db2 = MainDb.getDb(this) // создаем переменную и присваиваем ей значение
        db2.getDao().getAllItem().asLiveData().observe(this, Observer{
                lists->  // Заполняем список данными
            if (lists != null) {
                adapter.clear() // Очищаем список заполнения
                adapter.addAll(lists) // Заполняем список данными
                }
            }
        )

        binding.textView7.setOnClickListener{ // нажимаем на кнопку "добавить категорию"
            val item = Item(null,
                binding.editTextTextPersonName2.text.toString(), // поле ввода 1
                binding.editTextTextPersonName5.text.toString()) // поле ввода 2
            Toast.makeText(this, "Категория добавлена", Toast.LENGTH_LONG).show()
            Thread {
                db.getDao().insertItem(item) // Запуск псевдопараллельного потока
//                db2.getDao().insertItem(item) // Запуск псевдопараллельного потока
            }.start()
        }

//        binding.textView8.setOnClickListener{
//            Toast.makeText(this, "Категория удалена", Toast.LENGTH_LONG).show()
//        }
    }

            private fun init() {
                binding.rcView.layoutManager = LinearLayoutManager(this@MainActivity)
                binding.rcView.adapter = adapter2
        binding.textView8.setOnClickListener {
                if(index > 4) index = 0
                val plant = Plant(imageIdList[index], "Plant $index")
                adapter2.addPlant(plant)
                index++
            val plantList = null
            Toast.makeText(this, "Добавлен eще один элемент RecyclerView", Toast.LENGTH_SHORT).show()
        }
    }
}

