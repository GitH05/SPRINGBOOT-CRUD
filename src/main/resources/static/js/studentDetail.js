$(document).ready(function () {
    studentDetailGrid();
});

function studentDetailGrid() {
    $("#grid").jqGrid({
        //url: "get-student-detail",
        datatype: "json",
        //mtype: "GET",

        //colNames: ["ID", "First Name", "Address", "Department", "Action"],
        colModel: [
            { name: "id", key: true, index:'id', editable: false, hidden: true },
            { name: "name", index:'name', align: "center", editable: true },
            { name: "address", index:'address', align: "center",  editable: true },
            { name: "department", index:'department', align: "center",  editable: true },
            { name: "action", index:'action', align: "center", editable: true }
        ],

        pager: "#pager",
        rowNum: 10,
        rowList: [10, 20, 50],
        //sortname: "id",
        //sortorder: "asc",
        viewrecords: true,
        //height: "auto",
        //caption: "Employees",
        //autowidth: true,

        // JSON mapping
        //jsonReader: { repeatitems: false },
        loadonce: true
    });
}