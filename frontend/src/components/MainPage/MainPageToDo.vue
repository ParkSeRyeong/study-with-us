<template>
  <div class="maintodo">
    <div class="container">
      <div class="row justify-content-between">
        <h1>TO-DO LIST</h1>
        <i v-if="!this.editState" @click="editTodo()" class="icon fas fa-edit"></i>
        <i v-else @click="editTodo()" class="icon far fa-save"></i>
      </div>
    </div>
    <form
      v-if="this.editState"
      @submit.prevent="addTodo()"
    >
      <div class="container">
        <div class="row justify-content-between">
          <div class="col-10">
            <input
              v-model="newTodo"
              name="newTodo"
              autocomplete="off"
              placeholder="할 일을 입력하세요."
              class="col-9-1"
            >
          </div>
          <div class="col-2 todo-add-btn">
            <i @click="addTodo()" class="far fa-plus-square"></i>
          </div>
        </div>
      </div>
    </form>
    <ul>
      <li
        v-for="(todo, index) in this.todos"
        :key="index"
      >
        <div>
          <i v-if="!(todo)" @click="doneTodo(index)" class="far fa-square"></i>
          <i v-else @click="doneTodo(index)" class="far fa-check-square"></i>
          <span style="color: white">  .</span>
          <span
            :class="{ done: todo }"
            @click="doneTodo(index)"
          >{{ index }}</span>
        </div>
        <i
          v-if="this.editState"
          @click="removeTodo(index)"
          class="fas fa-trash-alt"
        ></i>
      </li>
    </ul>
    <h4 v-if="todos == null">할 일을 추가해주세요</h4>
  </div>
</template>

<script>
	// import { ref } from 'vue';
	export default {
		name: 'todo',
    data: function () {
      return {
        editState: false,
        newTodo: null,
        todos: {},
        today: null,
        todayYear: null,
        todayMonth: null,
        todayDate: null,
        todayText: null,
      }
    },
    created() {
      console.log(this.$store.state.mainPage)
      this.todos = this.$store.state.mainPage.todos
      console.log(this.todos)
      this.today = new Date()
      this.todayYear = this.today.getFullYear()
      this.todayMonth = ("0" + (this.today.getMonth() + 1)).slice(-2)
      this.todayDate = ("0" + this.today.getDate()).slice(-2)
      this.todayText = this.todayYear + '-' + this.todayMonth + '-' + this.todayDate
    },
    mounted() {

    },
    methods: {
      editTodo () {
        if (this.editState == false) {
          console.log(this.todos)
          this.editState = true
        } else {
          console.log(this.todos)
          if (!(this.todos == this.$store.state.mainPage.todo)) {
            this.$store.dispatch('mainPage/todoUpdate', this.todos)
          }
          this.todos = this.$store.state.mainPage.mainData.todo
          this.editState = false
        }
      },
      addTodo () {
        if (this.newTodo) {
          this.todos[this.newTodo] = false
          console.log(this.todos)
          this.newTodo = null
        }
      },
      doneTodo (index) {
        this.todos[index] = !this.todos[index]
        var data = {
          'day' : this.todayText,
          'done' : this.todos[index],
          'todo' : index
        }
        console.log(data)
        this.$store.dispatch('mainPage/todoCheck', data)
      },
      removeTodo (index) {
        delete this.todos[index]
      },
    },
		// setup () {
		// 	const newTodo = ref('');
		// 	const defaultData = [{
		// 		done: false,
		// 		content: 'Write a blog post'
		// 	}]
		// 	const todosData = JSON.parse(localStorage.getItem('todos')) || defaultData;
		// 	const todos = ref(todosData);
    //   function addTodo () {
		// 		if (newTodo.value) {
		// 			todos.value.push({
		// 				done: false,
		// 				content: newTodo.value
		// 			});
		// 			newTodo.value = '';
		// 		}
		// 		saveData();
		// 	}
		// 	function doneTodo (todo) {
		// 		todo.done = !todo.done
		// 		saveData();
		// 	}
		// 	function removeTodo (index) {
		// 		todos.value.splice(index, 1);
		// 		saveData();
		// 	}
		// 	function saveData () {
		// 		const storageData = JSON.stringify(todos.value);
		// 		localStorage.setItem('todos', storageData);
		// 	}
		// 	return {
		// 		todos,
		// 		newTodo,
		// 		addTodo,
		// 		doneTodo,
		// 		removeTodo,
		// 		saveData
		// 	}
		// }
	}
</script>

<style>
.maintodo h1 {
  font-weight: bold;
  font-size: 28px;
  text-align: left;
  flex: 0 0 auto;
  width: auto;
}

.maintodo .icon {
  font-weight: bold;
  font-size: 25px;
  text-align: left;
  flex: 0 0 auto;
  width: auto;
}

.maintodo form {
  display: flex;
  flex-direction: column;
  width: 100%;
}
.maintodo form label {
  font-size: 14px;
  font-weight: bold;
}
.maintodo form input,
.maintodo form button {
  height: 35px;
  box-shadow: none;
  outline: none;
  padding-left: 12px;
  padding-right: 12px;
  border-radius: 6px;
  font-size: 15px;
  margin-top: 6px;
  margin-bottom: 6px;
}
.maintodo form input {
  background-color: #f1f1f1;
  border: 2px solid rgba(255, 255, 255, 0);
  color: inherit;
}

.maintodo .col-9-1 {
  flex: 0 0 auto;
  width: 100%;
}

.maintodo .todo-add-btn {
  flex: 0 0 auto;
  width: 16.6666666667%;
  text-align: center;
  margin: 6px 0;
  font-size: 31px;
  padding: 0;
}

.maintodo button {
  cursor: pointer;
  background-color: #a0a4d9;
  border: 1px solid #a0a4d9;
  color: #1f2023;
  font-weight: bold;
  outline: none;
  border-radius: 6px;
}
.maintodo h2 {
  font-size: 22px;
  border-bottom: 2px solid rgba(255, 255, 255, 0.35);
  padding-bottom: 6px;
}
.maintodo ul {
  padding: 10px;
}
.maintodo ul li {
  display: flex;
  justify-content: space-between;
  align-items: center;
  border: 2px solid rgba(255, 255, 255, 0.35);
  padding: 6px 6px;
  border-radius: 6px;
  margin-bottom: 6px;
}
.maintodo ul li span {
  cursor: pointer;
}
.maintodo ul li .done {
  text-decoration: line-through;
}
.maintodo ul li button {
  font-size: 12px;
  padding: 6px;
}
.maintodo h4 {
  text-align: center;
  opacity: 0.5;
  margin: 0;
}
</style>