package com.example.justi.eulerkt

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class ProblemList : AppCompatActivity() {

  val recycler by lazy {
    (findViewById(R.id.recycler) as RecyclerView).apply {
      layoutManager = LinearLayoutManager(context)
    }
  }

  val adapter = object: RecyclerView.Adapter<EulerViewHolder>() {

    val items = listOf(
      EulerProblem(1, {
        (1 until 1000).filter { it % 3 == 0 || it % 5 == 0 }.sum().toString()
      }),
      EulerProblem(2, {
        (1 until 10000)
          .map { fib(it) }
          .takeWhile { it < 4_000_000 }
          .filter { it % 2 == 0 }
          .sum().toString()
      })
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EulerViewHolder {
      return EulerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_euler_view_holder, parent, false))
    }

    override fun getItemCount(): Int = items.count()

    override fun onBindViewHolder(viewHolder: EulerViewHolder, index: Int) {
      with(items[index]) {
        viewHolder.problemNumber.text = "$number."
        viewHolder.solveButton.setOnClickListener {
          viewHolder.solution.setText(solution())
        }
      }
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_problem_list)

    recycler.adapter = adapter
  }
}


class EulerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
  val problemNumber by lazy {
    itemView.findViewById(R.id.problem_number) as TextView
  }

  val solveButton by lazy {
    itemView.findViewById(R.id.solve_button) as Button
  }

  val solution by lazy {
    itemView.findViewById(R.id.solution) as EditText
  }
}

class EulerProblem(val number: Int, val solution: () -> String)

val memory = mutableMapOf<Int, Int>(
  1 to 1,
  2 to 2
)
fun fib(n: Int): Int {
  if (memory.containsKey(n)) return memory[n]!!

  val result =
    if (n == 1) return 1
    else if (n == 2) return 2
    else fib(n - 1) + fib(n - 2)

  memory[n] = result

  return result
}