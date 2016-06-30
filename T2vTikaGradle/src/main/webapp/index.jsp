<!DOCTYPE html>
<html>
<head>
<title>Asynchronous file Upload in Java Web Application</title>
<script src="https://code.jquery.com/jquery-2.1.4.min.js"></script>
<script src="js/jQuery.AjaxFileUpload.js"></script>

<script>
	jQuery(document)
			.ready(
					function() {
						jQuery('input[type="file"]')
								.ajaxfileupload(
										{
											'action' : 'UploadFile',
											'onComplete' : function(response) {
												jQuery('#upload').hide();
												jQuery('#message').show();

												var statusVal = JSON
														.stringify(response.status);

												if (statusVal == "false") {
													jQuery("#message")
															.html(
																	"<font color='red'>"
																			+ JSON
																					.stringify(response.message)
																			+ "</font>");
												}
												if (statusVal == "true") {
													jQuery("#message")
															.html(
																	"<font color='green'>"
																			+ JSON
																					.stringify(response.message)
																			+ "</font>");
												}
											},
											'onStart' : function() {
												jQuery('#upload').show();
												jQuery('#message').hide();
											}
										});
					});
</script>
<style type="text/css">
.centered {
	width: 100%;
	margin-left: auto;
	margin-right: auto;
	text-align: center;
}
</style>
</head>
<body>
	<form>
		<div class="centered">
			<h4>AJAX Style File upload in Java Web Application</h4>
			<input type="file" name="file" /><br />
			<div id="upload" style="display: none;">Uploading..</div>
			<div id="message"></div>
		</div>
	</form>
</body>
</html>