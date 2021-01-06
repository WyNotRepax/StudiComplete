globGetMethod = 0; /* 0: html; 1: xyz */
globContext = "";
globInstantParam = "";
globInstant = true;

function setContext(context) {
    globContext = context;
}

function setInstantParam(instantParam) {
    globInstantParam = instantParam;
}

function setGetMethod(val) {
    globGetMethod = val;
}

function $(id) {
    return document.getElementById(id);
}

function getXMLHttpRequest() {
    // XMLHttpRequest for Firefox, Opera, Safari
    if (window.XMLHttpRequest) {
        return new XMLHttpRequest();
    }
    if (window.ActveObject) { // Internet Explorer
        try { // for IE new
            return new ActiveXObject("Msxml2.XMLHTTP");
        } catch (e) {  // for IE old
            try {
                return new ActiveXObject("Microsoft.XMLHTTP");
            } catch (e) {
                alert("Your browser does not support AJAX!");
                return null;
            }
        }
    }
    return null;
}

function getHttpRequest(url) {
    if (globGetMethod == 0)
        getHtmlHttpRequest(url);
    else
        getxyzHttpRequest(url);
}

function getHtmlHttpRequest(url) {
    console.log("getHtmlHttpRequest", url);
    var xmlhttp = getXMLHttpRequest();
    xmlhttp.open("GET", url, true);

    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState != 4) {
            $('posters').innerHTML = 'Seite wird geladen ...';
        }
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            $('posters').innerHTML = xmlhttp.responseText;
        }
        $('timestamp').innerHTML = new Date().toString();
    };
    xmlhttp.send(null);
}

function getxyzHttpRequest(url) {
    var fullUrl = globInstant ? `${url}?${globInstantParam}=true` : url;
    globInstant = false;
    console.log("getxyzHttpRequest", fullUrl)
    var xmlhttp = getXMLHttpRequest();
    xmlhttp.open("GET", fullUrl, true);
    xmlhttp.onreadystatechange = () => {
        if (xmlhttp.readyState != 4) {
            console.log("Loading...")
        }
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            data = JSON.parse(xmlhttp.responseText);
            if (data.billboard) {
                console.log("Billboard Changed");
                updateTable(data);
            }
            else{
                console.log("Billboard Unchanged");
            }
            getHttpRequest('BillBoardServlet');
        }
        $('timestamp').innerHTML = new Date().toString();
    }
    xmlhttp.send(null);
}

function postHttpRequest(url) {
    var contents = $("contents");
    var content = contents.value;
    if (content === "") {
        return;
    }
    contents.value = "";
    var xmlhttp = getXMLHttpRequest();
    xmlhttp.open("POST", url, true);
    xmlhttp.send(content);

}

function putHttpRequest(url, id) {
    console.log("getxyzHttpRequest", url, id)
    var content = $(`input_field_${id}`).value;
    var xmlhttp = getXMLHttpRequest();
    xmlhttp.onreadystatechange = () => {
        console.log("Put readystate chaged!", xmlhttp.readyState);
    }
    xmlhttp.open("POST", url, true);
    xmlhttp.send(`${id}\n${content}\n`);
}

function deleteHttpRequest(url, id) {
    var xmlhttp = getXMLHttpRequest();
    xmlhttp.open("DELETE", url, true);
    xmlhttp.send(id);
}

function updateTable(data) {
    var posters = $("posters");
    posters.innerText = "";

    var table = document.createElement("table");
    table.border = 1;
    table.rules = "none";
    table.cellspacing = 4;
    table.cellpadding = 5;


    console.log("Data", data);
    for (var poster of data.billboard) {
        const id = poster.id;
        var tr = document.createElement("tr");
        var id_td = document.createElement("td");
        id_td.innerText = poster.id;
        tr.appendChild(id_td);

        var input_td = document.createElement("td");
        var input = document.createElement("input");
        input.type = "text";
        input.size = 100;
        input.minlength = 100;
        input.maxlength = 100;
        input.id = `input_field_${id}`;
        input.value = poster.text;
        if (poster.disable_edits) {
            input.readOnly = true;
            input.style.backgroundColor = "#eeeeee";
        }
        input_td.appendChild(input);
        tr.append(input_td);
        if (!poster.disable_edits) {
            var update_button_td = document.createElement("td");
            var update_button = document.createElement("button");
            update_button.onclick = () => {
                putHttpRequest(data.context, id);
            };
            update_button.innerText = "Update";
            update_button_td.appendChild(update_button);
            tr.append(update_button_td);

            var delete_button_td = document.createElement("td");
            var delete_button = document.createElement("button");
            delete_button.onclick = () => {
                deleteHttpRequest(data.context, id);
            };
            delete_button.innerText = "Delete";
            delete_button_td.appendChild(delete_button);
            tr.append(delete_button_td);
        }



        table.append(tr);
    }
    posters.appendChild(table);
}