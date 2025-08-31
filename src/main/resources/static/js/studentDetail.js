$(document).ready(function() {
    // Initialize jqGrid
    $("#grid").jqGrid({
        url: "/get-student-detail-grid",
        datatype: "json",
        mtype: "GET",
        colNames: ['ID', 'Name', 'Address', 'Department', 'Active', 'Actions'],
        colModel: [
            { name: 'id', index: 'id', width: 50, align: 'center', key: true },
            { name: 'name', index: 'name', width: 150 },
            { name: 'address', index: 'address', width: 200 },
            { name: 'department', index: 'department', width: 150 },
            { name: 'active', index: 'active', width: 80, align: 'center' },
            { name: 'actions', index: 'actions', width: 100, sortable: false, formatter: actionButtons }
        ],
        pager: '#pager',
        rowNum: 10,
        rowList: [10, 20, 30],
        sortname: 'id',
        sortorder: 'asc',
        viewrecords: true,
        gridview: true,
        jsonReader: {
            root: 'rows',
            page: 'page',
            total: 'total',
            records: 'records'
        },
        loadComplete: function(data) {
            console.log(data);
        }
    });

    // Action buttons (Edit/Delete)
    function actionButtons(cellvalue, options, rowObject) {
        return `
            <button class="edit-btn" onclick="editStudent(${rowObject.id})">Edit</button>
            <button class="delete-btn" onclick="deleteStudent(${rowObject.id})">Delete</button>
        `;
    }

    // Form submission (Add/Update)
    $('#studentForm').submit(function(e) {
        e.preventDefault();

        // Simple validation (ensure required fields aren't empty)
        const name = $('#name').val();
        const address = $('#address').val();
        const department = $('#department').val();

        if (!name || !address || !department) {
            alert("Please fill in all the fields.");
            return;
        }

        const student = {
            id: $('#id').val(),
            name,
            address,
            department
        };

        const url = student.id ? `/update-student/${student.id}` : "/add-student";

        $.ajax({
            url: url,
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(student),
            success: function(response) {
                $('#studentForm')[0].reset();
                $("#grid").trigger('reloadGrid'); // Reload the grid after the action
            },
            error: function(err) {
                console.error(err);
                alert("Error saving student details.");
            }
        });
    });

    // Clear form
    $('#clear').click(function() {
        $('#studentForm')[0].reset();
    });
});

// Edit student details and populate the form
function editStudent(id) {
    $.get(`/get-student-detail?id=${id}`, function(data) {
        const student = data.rows[0];  // Assuming the response has `rows`
        $('#id').val(student.id);
        $('#name').val(student.name);
        $('#address').val(student.address);
        $('#department').val(student.department);
    });
}

// Delete student
function deleteStudent(id) {
    if (confirm("Are you sure you want to delete this student?")) {
        $.ajax({
            url: `/delete-student/${id}`,
            method: 'DELETE',
            success: function(response) {
                alert("Student deleted successfully.");
                $("#grid").trigger('reloadGrid');  // Reload the grid after deletion
            },
            error: function(err) {
                console.error(err);
                alert("Error deleting student.");
            }
        });
    }
}
