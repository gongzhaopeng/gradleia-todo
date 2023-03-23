package com.atzu68.learning.gia.todo.repository

import com.atzu68.learning.gia.todo.model.ToDoItem

import java.sql.*

class H2ToDoRepository implements ToDoRepository {

    static {
        Class.forName("org.h2.Driver")
    }

    @Override
    List<ToDoItem> findAll() {
        def sql = 'SELECT id, name, completed from todo_item'
        return executeSql({ Connection c -> c.prepareStatement(sql) },
                { PreparedStatement s -> s.executeQuery() },
                { ResultSet rs ->
                    List<ToDoItem> toDoItems = []
                    while (rs.next()) toDoItems << fromResultSetToToDoItem(rs)
                    return toDoItems
                })
    }

    @Override
    ToDoItem findById(Long id) {
        def sql = 'SELECT id, name, completed from todo_item WHERE id = ?'
        return executeSql({ Connection c -> c.prepareStatement(sql) },
                { PreparedStatement s ->
                    s.setLong(1, id)
                    s.executeQuery()
                },
                { ResultSet rs -> rs.first() ? fromResultSetToToDoItem(rs) : null })
    }

    @Override
    Long insert(ToDoItem toDoItem) {
        def sql = 'INSERT INTO todo_item (name, completed) VALUES (?,?)'
        return executeSql({ Connection c -> c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS) },
                { PreparedStatement s ->
                    s.setString(1, toDoItem.name)
                    s.setBoolean(2, toDoItem.completed)
                    s.executeUpdate()

                    s.getGeneratedKeys()
                },
                { ResultSet rs -> rs.first() ? rs.getLong(1) : null })
    }

    @Override
    def update(ToDoItem toDoItem) {
        def sql = 'UPDATE todo_item SET name = ?, completed = ? where id = ?'
        return executeSql({ Connection c -> c.prepareStatement(sql) },
                { PreparedStatement s ->
                    s.setString(1, toDoItem.name)
                    s.setBoolean(2, toDoItem.completed)
                    s.setLong(3, toDoItem.id)
                    s.executeUpdate()
                    null
                }, {})
    }

    @Override
    def delete(ToDoItem toDoItem) {
        def sql = 'DELETE FROM todo_item WHERE id = ?'
        return executeSql({ Connection c -> c.prepareStatement(sql) },
                { PreparedStatement s ->
                    s.setLong(1, toDoItem.id)
                    s.executeUpdate()
                    null
                }, {})
    }

    static private ToDoItem fromResultSetToToDoItem(ResultSet rs) {
        return new ToDoItem(id: rs.getLong('id'),
                name: rs.getString('name'),
                completed: rs.getBoolean('completed'))
    }

    static private <T> T executeSql(Closure<PreparedStatement> prepareStatement,
                                    Closure<ResultSet> generateResultSet, Closure<T> transformResultSet) {
        Connection conn = null
        Statement stmt = null
        ResultSet rs = null

        try {
            conn = createConnection()
            stmt = prepareStatement(conn)
            rs = generateResultSet(stmt)
            return transformResultSet(rs)
        } finally {
            closeConnection(conn);
            closeStatement(stmt);
            closeResultSet(rs);
        }
    }

    static private Connection createConnection() {
        return DriverManager.getConnection("jdbc:h2:~/todo", "sa", "");
    }

    static private void closeConnection(Connection connection) {
        connection?.close()
    }

    static private void closeStatement(Statement statement) {
        statement?.close()
    }

    static private void closeResultSet(ResultSet resultSet) {
        resultSet?.close()
    }
}
