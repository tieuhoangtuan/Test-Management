let selectCase = document.getElementById("includeSpecificContainer");
let radio1 = document.querySelector('input[name="include_all"]');
let radio2 = document.querySelector('input[name="include_one"]');
let request = document.getElementById("request");
// Array to store selected test cases
let selectedTestCases = [];


radio1.addEventListener("change", function() {
    if(this.checked === true) {
        radio2.checked = false;
        request.value = 1;
        selectCase.style.display = "none";
    }
});
radio2.addEventListener("change", function() {
    if(this.checked === true) {
        radio1.checked = false;
        request.value = 2;
        selectCase.style.display = "block";

    } else {
        request.value = 1;
        selectCase.style.display = "none";
    }
});

document.getElementById("show-form-button").addEventListener("click", function () {
    event.preventDefault();
    document.getElementById("overlay").style.display = "block";
});

document.getElementById("close-button").addEventListener("click", function () {
    event.preventDefault();
    document.getElementById("overlay").style.display = "none";
});

document.getElementById("ok-button").addEventListener("click", function () {
    event.preventDefault();
    let testCaseCountElem = document.getElementById("testCaseCount");
    let testCaseListInput = document.getElementById("testCaseList");
    testCaseListInput.value = selectedTestCases.join(",");
    console.log(testCaseListInput.value);
    testCaseCountElem.textContent = selectedTestCases.length.toString();
    document.getElementById("overlay").style.display = "none";
})


// handle load test case by ajax and section
document.addEventListener("DOMContentLoaded", function () {
    let sectionLinks = document.querySelectorAll(".section-link");
    sectionLinks.forEach(function (link) {
        link.addEventListener("click", function (e) {
            e.preventDefault();
            let projectId = this.getAttribute('data-project-id');
            let sectionId = this.getAttribute('data-section-id');
            loadTestCaseDetails(projectId, sectionId);
        });
    });

    // main method to call ajax
    function loadTestCaseDetails(projectId, sectionId) {
        fetch('/cases/get/' + projectId + '/' + sectionId)
            .then(function(response) {
                if (response.ok) {
                    return response.json();
                } else {
                    throw new Error('Error: ' + response.status);
                }
            })
            .then(function(data) {
                // console.log(data);
                updatePopupForm(data);
            })
            .catch(function(error) {
                console.error(error);
            });
    }


    // Use DOM to create checkbox and assign value
    function updatePopupForm(data) {
        let testCaseForm = document.getElementById('test-case-form');
        let sectionText = document.createElement('div');
        sectionText.className = 'text';

        // Clear the existing content
        testCaseForm.innerHTML = '';

        // append section name element
        if (data === null || data.length === 0) {
            sectionText.textContent = "No Data";
            testCaseForm.appendChild(sectionText);
        }

        data.forEach(function(testCase) {
            let testCaseDiv = document.createElement('div');
            let testCaseLabel = document.createElement('label');
            let testCaseCheckbox = document.createElement('input');

            testCaseDiv.className = 'test-case-item';
            sectionText.textContent = testCase.section;

            testCaseCheckbox.type = 'checkbox';
            testCaseCheckbox.value = testCase.id;

            testCaseLabel.appendChild(testCaseCheckbox);
            testCaseLabel.appendChild(document.createTextNode(testCase.title));

            testCaseDiv.appendChild(testCaseLabel);

            testCaseForm.appendChild(testCaseDiv);
            testCaseCheckbox.onclick = function () {
                handleCheckboxClick(testCaseCheckbox);
            };
        });
    }

    // Add click event for checkbox to store the selected test cases list
    function handleCheckboxClick(checkbox) {
        if (checkbox.checked) {
            selectedTestCases.push(checkbox.value); // Add selected test case to the array
            // console.log(selectedTestCases);
        } else {
            let index = selectedTestCases.indexOf(checkbox.value);

            if (index > -1) {
                selectedTestCases.splice(index, 1); // Remove the test case from the array if uncheck
                // console.log(selectedTestCases);
            }
        }
    }

});