
      $(document).ready(function () {
        //hide the error section
        $("#specCodeError").hide();
        $("#specNameError").hide();
        $("#specNoteError").hide();
        //define the error variable
        var specCodeError = false;
        var specNameError = false;
        var specNoteError = false;
        //validate the form
        function validate_specCode() {
          var val = $("#specCode").val();
          var exp = /^[A-Z]{4,8}$/;
          if (val == 0) {
            $("#specCodeError").show();
            $("#specCodeError").html("*please enter the <b>name</b>");
            $("#specCodeError").css("color", "red");
            specCodeError = false;
          } else if (!exp.test(val)) {
            $("#specCodeError").show();
            $("#specCodeError").html("*please enter 4-8 <b>charaters</b>");
            $("#specCodeError").css("color", "red");
            specCodeError = false;
          } else {
	                 
	        $.ajax({
		                url: ' checkCode',
		                data:{"code":val},
		                success:function(resTxt) {
			            if(resTxt!=""){
				            $("#specCodeError").show();
                             $("#specCodeError").html(resTxt);
                             $("#specCodeError").css("color", "red");
                              specCodeError = false;
			            } else{
				                    $("#specCodeError").hide();
                                     specCodeError = true;
			           }
		            }		
	           });
          }
          return specCodeError;
        }

        function validate_specName() {
          var val = $("#specName").val();
          var exp = /^[A-Za-z\s\.\/\-]{4,30}$/;
          if (val == 0) {
            $("#specNameError").show();
            $("#specNameError").html("*please enter the <b>specialization</b>");
            $("#specNameError").css("color", "red");
            specNameError = false;
          } else if (!exp.test(val)) {
            $("#specNameError").show();
            $("#specNameError").html("*please enter 4-30 <b>charaters</b>");
            $("#specNameError").css("color", "red");
            specNameError = false;
          } else {
           $.ajax({
	                   url: 'checkName',
	                   data: {'name':val},
	                   success:function(resTxt){
		               if(resTxt!=""){
			                $("#specNameError").show();
                            $("#specNameError").html(resTxt);
                            $("#specNameError").css("color", "red");
                            specNameError = false;
		               }else{
			                     $("#specNameError").hide();
			                     specNameError = true;
		              }
	              }
            });
          }
           return specNameError;
        }

        function validate_specNote() {
          var val = $("#specNote").val();
          var exp = /^[A-Za-z\,\.\s\?\-\`]{12,250}$/;
          if (val == 0) {
            $("#specNoteError").show();
            $("#specNoteError").html("*please enter the <b>note</b>");
            $("#specNoteError").css("color", "red");
            specNoteError = false;
          } else if (!exp.test(val)) {
            $("#specNoteError").show();
            $("#specNoteError").html("*please enter 12-250 <b>charaters</b>");
            $("#specNoteError").css("color", "red");
            specNoteError = false;
          } else {
            $.ajax({
	                      url:'checkNote',
	                      data:{'note':val},
	                      success:function(resTxt){
		                   if(resTxt!=""){
			                       $("#specNoteError").show();
			                       $("#specNoteError").html(resTxt);
			                       $("#specNoteError").css("color", "red");
                                    specNoteError = false;
			
		                   }else{
			                         $("#specNoteError").hide();
			                         specNoteError = true;
		                  }
	                  }
                });
          }
          return specNoteError;
        }
        //link with action-event
        $("#specCode").keyup(function () {
          //set the value
          $(this).val($(this).val().toUpperCase());
          validate_specCode();
        });
        $("#specName").keyup(function () {
          validate_specName();
        });
        $("#specNote").keyup(function () {
          validate_specNote();
        });
        //on-submit
        $("#specForm").submit(function () {
          validate_specCode();
          validate_specName();
          validate_specNote();
          if (specCodeError && specNameError && specNoteError) return true;
          else return false;
        });
      });
   
