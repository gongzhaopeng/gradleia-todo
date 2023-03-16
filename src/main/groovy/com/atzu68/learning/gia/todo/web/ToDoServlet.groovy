package com.atzu68.learning.gia.todo.web

import com.atzu68.learning.gia.todo.model.ToDoItem
import com.atzu68.learning.gia.todo.repository.InMemoryToDoRepository

import javax.servlet.RequestDispatcher
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class ToDoServlet extends HttpServlet {
    static final String FIND_ALL_SERVLET_PATH = "/all"
    static final String INDEX_PAGE = "/jsp/todo-list.jsp"
    private def toDoRepository = new InMemoryToDoRepository()

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) {
        String servletPath = request.getServletPath()
        String view = processRequest(servletPath, request)
        RequestDispatcher dispatcher = request.getRequestDispatcher(view)
        dispatcher.forward(request, response)
    }

    private String processRequest(String servletPath, HttpServletRequest request) {
        switch (servletPath) {
            case FIND_ALL_SERVLET_PATH:
                def toDoItems = toDoRepository.findAll()
                request.setAttribute("toDoItems", toDoItems)
                request.setAttribute("stats", determineStats(toDoItems))
                request.setAttribute("filter", "all")
                return INDEX_PAGE
            case "/active":
                def toDoItems = toDoRepository.findAll()
                request.setAttribute("toDoItems", filterBasedOnStatus(toDoItems, true))
                request.setAttribute("stats", determineStats(toDoItems))
                request.setAttribute("filter", "active")
                return INDEX_PAGE
            case "/completed":
                def toDoItems = toDoRepository.findAll()
                request.setAttribute("toDoItems", filterBasedOnStatus(toDoItems, false))
                request.setAttribute("stats", determineStats(toDoItems))
                request.setAttribute("filter", "completed")
                return INDEX_PAGE
            case "/insert":
                ToDoItem toDoItem = [name: request.getParameter("name")]
                toDoRepository.insert(toDoItem)
                return "/" + request.getParameter("filter")
            case "/update":
                def toDoItem = toDoRepository.findById(request.getParameter("id").toLong())

                toDoItem?.with {
                    name = request.getParameter("name")
                    toDoRepository.update(it)
                }

                return "/" + request.getParameter("filter")
            case "/delete":
                def toDoItem = toDoRepository.findById(request.getParameter("id").toLong())

                toDoItem?.with { toDoRepository.delete(it) }

                return "/" + request.getParameter("filter")
            case "/toggleStatus":
                def toDoItem = toDoRepository.findById(request.getParameter("id").toLong())

                toDoItem?.with {
                    completed = request.getParameter("toggle") == "on"
                    toDoRepository.update(it)
                }

                return "/" + request.getParameter("filter")
            case "/clearCompleted":
                def toDoItems = toDoRepository.findAll()

                toDoItems.forEach {
                    if (it.completed) toDoRepository.delete(it)
                }

                return "/" + request.getParameter("filter")
            default: return FIND_ALL_SERVLET_PATH
        }
    }

    static private List<ToDoItem> filterBasedOnStatus(List<ToDoItem> toDoItems, boolean active) {

        return toDoItems.findAll { it.completed != active }
    }

    private ToDoListStats determineStats(List<ToDoItem> toDoItems) {
        def toDoListStats = new ToDoListStats()

        toDoItems.forEach {
            it.completed ? toDoListStats.addCompleted() : toDoListStats.addActive()
        }

        return toDoListStats
    }

    class ToDoListStats {
        private int active
        private int completed

        private void addActive() {
            active++
        }

        private void addCompleted() {
            completed++
        }

        int getActive() {
            return active
        }

        int getCompleted() {
            return completed
        }

        int getAll() {
            return active + completed
        }
    }
}
