<template>
  <div class="maintodo">
    <h1>TO-DO LIST</h1>
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
      </li>
    </ul>
    <h4 v-if="Object.keys(todos).length == 0">할 일이 등록되지 않았습니다.</h4>
  </div>
</template>

<script>
	// import { ref } from 'vue';
	export default {
		name: 'todo',
    computed: {
      todos: function () {
        return this.$store.state.daily_diary.todos
      }
    },
    data: function () {
      return {
        editState: false,
        newTodo: null,
        today: null,
        todayYear: null,
        todayMonth: null,
        todayDate: null,
        todayText: null,
      }
    },
    created() {
      this.today = new Date()
      this.todayYear = this.today.getFullYear()
      this.todayMonth = ("0" + (this.today.getMonth() + 1)).slice(-2)
      this.todayDate = ("0" + this.today.getDate()).slice(-2)
      this.todayText = this.todayYear + '-' + this.todayMonth + '-' + this.todayDate
    },
    methods: {
      doneTodo (index) {
        this.todos[index] = !this.todos[index]
        var data = {
          'day' : this.$store.state.daily_diary.date,
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
	}
</script>

<style>
.maintodo h1 {
  font-weight: bold;
  font-size: 28px;
  text-align: left;
  flex: 0 0 auto;
  width: auto;
  font-family: "NanumBarunGothic-Bold";
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