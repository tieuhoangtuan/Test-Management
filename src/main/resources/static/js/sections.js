function displayCaseForm(clicked_id){
    document.getElementById(`section${clicked_id}`).style.display = "block";
    document.getElementById(`inlineSectionActions${clicked_id}`).style.display = "none";
}

function closeCaseForm(clicked_id){
    document.getElementById(`section${clicked_id}`).style.display="none";
    document.getElementById(`inlineSectionActions${clicked_id}`).style.display ="block";
}

function displaySubSectionForm(clicked_id){
    const parentSectionId = document.getElementById(`${clicked_id}`);
    const value = parentSectionId.getAttribute('value');
    const addSectionForm = document.getElementById("addSubSectionForm");
    const dataParentSectionId = addSectionForm.querySelector('input[name="parentSectionId"]');
    addSectionForm.style.display = "block";
    dataParentSectionId.value = value;
    document.getElementById("ui-overlay").style.display = "block";
}
function editSection(id){
    const name = document.getElementById(`section-name-${id}`).textContent;
    const description = document.getElementById(`section-description-${id}`).textContent;

    document.getElementById("editSubSectionForm").style.display = "block";
    document.getElementById("ui-overlay").style.display = "block";
    document.getElementById("editId").value = id;
    document.getElementById("editName").value = name;
    document.getElementById("editDescription").value = description;
}

function closeSectionForm(){
    document.getElementById("addSubSectionForm").style.display="none";
    document.getElementById("ui-overlay").style.display = "none";
}

function closeEditSectionForm(){
    document.getElementById("editSubSectionForm").style.display="none";
    document.getElementById("ui-overlay").style.display = "none";
}

function dragTestCase(event, idTestcase) {
    event.dataTransfer.setData('text/plain', JSON.stringify({id: idTestcase}));
}

function allowDrop(event){
    event.preventDefault();
}

function dropTestCase(event, sectionId){
    event.preventDefault();
    const data = JSON.parse(event.dataTransfer.getData('text/plain'));
    const projectId = document.getElementById("contentSectionofproject").value;
    const formData = new FormData();
    formData.append('caseId', data.id);
    formData.append('sectionId', sectionId);
    console.log(`id test case: ${data.id}`);
    const xhr = new XMLHttpRequest();
    xhr.open('POST',`/cases/${projectId}/copy`, true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                // Copy successful, handle the response or update UI
                console.log('Test case copied successfully.');
                location.reload();
            }
            else if (xhr.status === 302) {
                const redirectUrl = xhr.getResponseHeader("Location");
                window.location.replace(redirectUrl);
            }
            else {
                // Copy failed, handle the error
                console.error('Failed to copy test case.');
            }
        }
    };
    xhr.send(formData);
}