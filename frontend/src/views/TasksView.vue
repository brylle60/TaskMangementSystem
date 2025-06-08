<template>
  <div class="max-w-6xl mx-auto">
    <div class="flex justify-between items-center mb-8">
      <h2 class="text-3xl font-bold text-gray-800">Tasks</h2>
      <button 
        @click="showCreateForm = true" 
        class="btn-primary"
      >
        <i class="fas fa-plus mr-2"></i>Add New Task
      </button>
    </div>

    <!-- Create Task Form -->
    <div v-if="showCreateForm" class="task-card mb-6">
      <h3 class="text-xl font-semibold mb-4">Create New Task</h3>
      <form @submit.prevent="createTask">
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4 mb-4">
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">Title</label>
            <input 
              v-model="newTask.title" 
              type="text" 
              required
              class="w-full px-3 py-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
            >
          </div>
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">Status</label>
            <select 
              v-model="newTask.status"
              class="w-full px-3 py-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
            >
              <option value="PENDING">Pending</option>
              <option value="IN_PROGRESS">In Progress</option>
              <option value="COMPLETED">Completed</option>
            </select>
          </div>
        </div>
        <div class="mb-4">
          <label class="block text-sm font-medium text-gray-700 mb-2">Description</label>
          <textarea 
            v-model="newTask.description" 
            rows="3"
            class="w-full px-3 py-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
          ></textarea>
        </div>
        <div class="flex space-x-2">
          <button type="submit" class="btn-primary" :disabled="loading">
            {{ loading ? 'Creating...' : 'Create Task' }}
          </button>
          <button type="button" @click="cancelCreate" class="btn-secondary">
            Cancel
          </button>
        </div>
      </form>
    </div>

    <!-- Tasks List -->
    <div v-if="loading && tasks.length === 0" class="text-center py-8">
      <div class="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600 mx-auto"></div>
      <p class="mt-4 text-gray-600">Loading tasks...</p>
    </div>

    <div v-else-if="tasks.length === 0" class="text-center py-8">
      <p class="text-gray-600">No tasks found. Create your first task!</p>
    </div>

    <div v-else class="grid gap-6">
      <div 
        v-for="task in tasks" 
        :key="task.id"
        class="task-card"
      >
        <div class="flex justify-between items-start mb-4">
          <div class="flex-1">
            <h3 class="text-xl font-semibold text-gray-800 mb-2">{{ task.title }}</h3>
            <p class="text-gray-600 mb-3">{{ task.description }}</p>
            <div class="flex items-center space-x-4">
              <span 
                :class="getStatusClass(task.status)"
                class="px-3 py-1 rounded-full text-sm font-medium"
              >
                {{ task.status }}
              </span>
              <span class="text-sm text-gray-500">
                Created: {{ formatDate(task.createdAt) }}
              </span>
            </div>
          </div>
          <div class="flex space-x-2 ml-4">
            <button 
              @click="editTask(task)"
              class="text-blue-600 hover:text-blue-800"
            >
              <i class="fas fa-edit"></i>
            </button>
            <button 
              @click="deleteTask(task.id)"
              class="text-red-600 hover:text-red-800"
            >
              <i class="fas fa-trash"></i>
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- Error Message -->
    <div v-if="error" class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mt-4">
      {{ error }}
    </div>
  </div>
</template>

<script>
import taskService from '@/services/taskService'

export default {
  name: 'TasksView',
  data() {
    return {
      tasks: [],
      loading: false,
      error: null,
      showCreateForm: false,
      newTask: {
        title: '',
        description: '',
        status: 'PENDING'
      }
    }
  },
  async mounted() {
    await this.fetchTasks()
  },
  methods: {
    async fetchTasks() {
      this.loading = true
      this.error = null
      
      try {
        const response = await taskService.getAllTasks()
        this.tasks = response.data
      } catch (error) {
        this.error = 'Failed to load tasks: ' + error.message
        console.error('Error fetching tasks:', error)
      } finally {
        this.loading = false
      }
    },

    async createTask() {
      this.loading = true
      this.error = null
      
      try {
        const response = await taskService.createTask(this.newTask)
        this.tasks.unshift(response.data)
        this.cancelCreate()
      } catch (error) {
        this.error = 'Failed to create task: ' + error.message
        console.error('Error creating task:', error)
      } finally {
        this.loading = false
      }
    },

    async deleteTask(id) {
      if (!confirm('Are you sure you want to delete this task?')) return
      
      try {
        await taskService.deleteTask(id)
        this.tasks = this.tasks.filter(task => task.id !== id)
      } catch (error) {
        this.error = 'Failed to delete task: ' + error.message
        console.error('Error deleting task:', error)
      }
    },

    editTask(task) {
      // Implement edit functionality
      console.log('Edit task:', task)
    },

    cancelCreate() {
      this.showCreateForm = false
      this.newTask = {
        title: '',
        description: '',
        status: 'PENDING'
      }
    },

    getStatusClass(status) {
      const classes = {
        'PENDING': 'bg-yellow-100 text-yellow-800',
        'IN_PROGRESS': 'bg-blue-100 text-blue-800',
        'COMPLETED': 'bg-green-100 text-green-800'
      }
      return classes[status] || 'bg-gray-100 text-gray-800'
    },

    formatDate(dateString) {
      return new Date(dateString).toLocaleDateString()
    }
  }
}
</script>