var app = angular.module('dataColl', []);

app
		.controller(
				'CreateOrEditFieldController',
				function($scope, $http, getCreateEditFieldJson) {

					$scope.options = [];
					$scope.types = [ "Textarea", "Radio button", "Checkbox",
							"Combobox", "Date" ];
					$scope.uuid = document.getElementById("uuid").value;

					/**
					 * Init everything.
					 */
					getCreateEditFieldJson
							.getData()
							.then(
									function(data) {
										var fieldToEdit = data.fieldToEdit;

										if (fieldToEdit.label != null) {
											var label = document
													.getElementById("label");
											var type = document
													.getElementById("types");
											var required = document
													.getElementById("required");
											var active = document
													.getElementById("active");

											label.value = fieldToEdit.label;
											type.value = fieldToEdit.type;
											if (fieldToEdit.required) {
												required.checked = true;
											}
											if (fieldToEdit.active) {
												active.checked = true;
											}
											if (fieldToEdit.type != "Textarea"
													&& fieldToEdit.type != "Date") {
												$scope.options = fieldToEdit.options;
											}
											document
													.getElementById("present-in-db").value = true;
										} else {
											document
													.getElementById("present-in-db").value = false;
										}
									});

					/**
					 * Sets $scope.fieldToEdit.
					 */
					$scope.setFieldToEdit = function(fieldToEdit) {
						$scope.fieldToEdit = fieldToEdit;
					}

					/**
					 * Adds an option.
					 */
					$scope.insertOption = function() {
						var optionText = document.getElementById("option").value;
						if (optionText.length == 0) {
							window.alert('Empty options are not allowed!');
							return;
						}

						for (i = 0; i < options.length; i++) {
							if ($scope.options[i] == optionText) {
								window
										.alert('The option is alredy in the list. Duplicates are not allowed!');
								return;
							}
						}

						$scope.options.push(optionText);
					}

					/**
					 * Removes selected option.
					 */
					$scope.removeOption = function() {
						var optionsSel = document.getElementById("options");
						var selIndex = optionsSel.selectedIndex;

						if (selIndex != -1) {
							$scope.options.splice(selIndex, 1);
						}
						if (optionsSel.length > 0) {
							optionsSel.selectedIndex = selIndex == 0 ? 0
									: selIndex - 1;
						}
					}

					/**
					 * A reaction to a type selection.
					 */
					$scope.changeSelType = function() {
						var typesSel = document.getElementById("types");
						var optionsSel = document.getElementById("options");
						var selIndex = typesSel.selectedIndex;

						if ($scope.types[selIndex] == "Textarea"
								|| $scope.types[selIndex] == "Date") {
							$scope.hideOptions = true;
							optionsSel.selectedIndex = 0;
						} else {
							$scope.hideOptions = false;
							var options = optionsSel.options;
							if (optionsSel.options.length > 0) {
								for (i = 1; i < options.length; i++) {
									options[i].selected = true;
								}
							}
						}
					}
				});

app.service('getCreateEditFieldJson', function($q, $http) {
	return {
		getData : function() {
			var uuid = document.getElementById("uuid").value;
			var defer = $q.defer();
			$http.get('/fields/' + uuid + '/json').success(function(data) {
				defer.resolve(data);
			});

			return defer.promise;
		}
	}
});

app.controller('FieldsController', function($scope, $http, getFieldsJson) {
	$scope.allFields = null;

	getFieldsJson.getData().then(function(data) {
		$scope.allFields = data.allFields;
	});
	
	$scope.isDisabled = function(field) {
		if (field.active) {
			return true;
		}
		return false;
	}
});

app.service('getFieldsJson', function($q, $http) {
	return {
		getData : function() {
			var defer = $q.defer();
			$http.get('/fields/json').success(function(data) {
				defer.resolve(data);
			});

			return defer.promise;
		}
	}
});

app.controller('ResponsesController', function($scope, $http, getResponsesJson) {
	$scope.allFields = null;
	$scope.allUsers = null;

	getResponsesJson.getData().then(function(data) {
		$scope.allFields = data.allFields;
		$scope.allUsers = data.allUsers;
	});
	
	this.showValue = function(value) {
		if (value.value != null) {
			return true;
		}
		
		return false;
	}
});

app.service('getResponsesJson', function($q, $http) {
	return {
		getData : function() {
			var defer = $q.defer();
			$http.get('responses/json').success(function(data) {
				defer.resolve(data);
			});

			return defer.promise;
		}
	}
});

app.controller('IndexController', function($scope, $http, getIndexJson) {
	$scope.allFields = null;

	getIndexJson.getData().then(function(data) {
		$scope.allFields = data.allFields;
	});
});

app.service('getIndexJson', function($q, $http) {
	return {
		getData : function() {
			var defer = $q.defer();
			$http.get('/json').success(function(data) {
				defer.resolve(data);
			});

			return defer.promise;
		}
	}
});
