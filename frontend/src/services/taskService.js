import axios from 'axios'

const API_BASE_URL = 'http://localhost:8080/api'

const apiClient = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
})

export default {
  // Task API calls
  getAllTasks() {
    return apiClient.get('/tasks')
  },
  
  createTask(taskData) {
    return apiClient.post('/tasks', taskData)
  },
  
  updateTask(id, taskData) {
    return apiClient.put(`/tasks/${id}`, taskData)
  },
  
  deleteTask(id) {
    return apiClient.delete(`/tasks/${id}`)
  }
}