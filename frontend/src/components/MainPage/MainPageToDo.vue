<template>
  <div
    class="maintodo"
    style="overflow-y:auto; overflow-x:hidden; width:100%; height:150px;"
  >
    <div class="container">
      <div class="row justify-content-between hyemin pb-2">
        <div class="d-flex">
          <h1 class="highlightTodo">TO-DO LIST</h1>
          <img src="@/assets/images/check.png" class="checkIcon" />
          <i
            id="editIcon"
            v-if="!this.editState"
            @click="editTodo()"
            class="icon fas fa-pencil-alt editIcon"
          ></i>
          <i v-else @click="editTodo()" class="icon fas fa-times"></i>
        </div>
      </div>
    </div>

    <!-- todo edit start -->
    <form v-if="this.editState" @submit.prevent="addTodo()">
      <div class="container">
        <div class="row justify-content-between align-items-center pb-1 pt-1">
          <div class="col-10">
            <input
              v-model="newTodo"
              name="newTodo"
              autocomplete="off"
              placeholder="할 일을 입력하세요."
              class="col-9-1 nanum"
            />
          </div>
          <div class="col-2 todo-add-btn">
            <i @click="addTodo()" class="fas fa-plus addIcon"></i>
          </div>
        </div>
      </div>
    </form>
    <!-- todo edit end -->

    <ul>
      <!-- todo item start -->
      <li v-for="(todo, index) in this.todos" :key="index" class="nanum">
        <div class="d-flex">
          <i
            v-if="!todo"
            @click="doneTodo(index)"
            class="far fa-square checkItem"
          ></i>
          <i
            v-else
            @click="doneTodo(index)"
            class="far fa-check-square checkItem"
          ></i>
          <span style="color: white"> .</span>
          <span
            :class="{ done: todo }"
            @click="doneTodo(index)"
            class="makeCenter"
            >{{ index }}</span
          >
        </div>
        <i
          v-if="this.editState"
          @click="removeTodo(index)"
          class="fas fa-trash-alt"
        ></i>
      </li>
      <!-- todo item end -->
    </ul>
    <h4 v-if="Object.keys(todos).length == 0">할 일을 추가해주세요</h4>
  </div>
</template>

<script>
export default {
  name: "todo",
  computed: {
    todos: function() {
      return this.$store.state.mainPage.todos;
    },
  },
  data: function() {
    return {
      editState: false,
      newTodo: null,
      today: null,
      todayYear: null,
      todayMonth: null,
      todayDate: null,
      todayText: null,
    };
  },
  created() {
    this.today = new Date();
    this.todayYear = this.today.getFullYear();
    this.todayMonth = ("0" + (this.today.getMonth() + 1)).slice(-2);
    this.todayDate = ("0" + this.today.getDate()).slice(-2);
    this.todayText =
      this.todayYear + "-" + this.todayMonth + "-" + this.todayDate;
  },
  methods: {
    editTodo() {
      if (this.editState == false) {
        console.log(this.todos);
        this.editState = true;
      } else {
        console.log(this.todos);
        if (!(this.todos == this.$store.state.mainPage.todo)) {
          this.$store.dispatch("mainPage/todoUpdate", this.todos);
        }
        this.todos = this.$store.state.mainPage.mainData.todo;
        this.editState = false;
      }
    },
    addTodo() {
      if (this.newTodo) {
        console.log(this.todos);
        this.todos[this.newTodo] = false;
        this.newTodo = null;
      }
    },
    doneTodo(index) {
      this.todos[index] = !this.todos[index];
      var data = {
        day: this.todayText,
        done: this.todos[index],
        todo: index,
      };
      console.log(data);
      this.$store.dispatch("mainPage/todoCheck", data);
    },
    removeTodo(index) {
      delete this.todos[index];
    },
  },
};
</script>

<style scoped>
.addIcon {
  color: #48d9df;
  font-size: 4vw;
  display: flex;
  align-items: center;
  margin-left: 4vw;
}
.checkIcon {
  height: 4vh;
  margin-left: 4vw;
  display: flex;
  align-items: center;
}
.makeCenter {
  display: flex;
  justify-items: center;
  align-items: center;
}
.checkItem {
  display: flex;
  justify-items: center;
  align-items: center;
  font-size: 6vw;
  padding-right: 2vw;
}
.hyemin {
  font-family: "IM_Hyemin-Regular";
  font-weight: 600;
  font-size: 4vw;
}
.maintodo h1 {
  font-weight: 900;
  font-size: 28px;
  font-size: 8vw;
  text-align: left;
  font-family: "IM_Hyemin-Regular";
  flex: 0 0 auto;
  width: auto;
  align-items: center;
}
.highlightTodo {
  background: linear-gradient(to top, #a4f9fc 40%, transparent 30%);
}

.maintodo .icon {
  font-weight: bold;
  font-size: 25px;
  text-align: left;
  flex: 0 0 auto;
  width: auto;
  display: flex;
  justify-content: space-around;
  margin-left: 25vw;
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
