package com.jercey.ws;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.BodyPart;
import org.glassfish.jersey.media.multipart.ContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import org.glassfish.jersey.media.multipart.file.StreamDataBodyPart;

import com.jercey.ws.util.ReadFromPropertiesFiles;
import com.jercey.ws.valObj.Author;
import com.jercey.ws.valObj2.Author2;

@Path("/v1")
public class Controller {

	@POST
	@Path("/jaxblist")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response jaxblist(Author author) throws IOException {

		Author2 list2 = new Author2();
		list2.setFirstName(author.getFirstName());
		list2.setLastName(author.getLastName());
		list2.setRequestType(author.getRequestType());
		list2.getFileKeys().add("srt1");
		list2.getFileKeys().add("srt2");
		list2.getFileKeys().add("srt3");
		list2.getFileKeys().add("srt4");
		list2.getFileKeys().addAll(author.getFileKeys());
		list2.setZoomint(1);
		list2.getZoomList().add(5);
		list2.getZoomList().add(6);
		list2.getZoomList().add(7);
		list2.getZoomList().add(8);

		return Response.status(Response.Status.OK).entity(list2).build();
	}

	@POST
	@Path("/ping")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response ping(Author author) throws IOException {
		/*
		 * Author author = new Author(); author.setFirstName("nitish");
		 * author.setLastName("VK"); author.setRequestType("LHS");
		 */

		ReadFromPropertiesFiles readFromPropertiesFiles = ReadFromPropertiesFiles.getInstance();
		String key1 = readFromPropertiesFiles.getPropertyMap().get("fileUploadDirir");
		String key2 = readFromPropertiesFiles.getPropertyMap().get("abc");
		String key3 = readFromPropertiesFiles.getPropertyMap().get("qwerty");
		String key4 = readFromPropertiesFiles.getPropertyMap().get("lank");
		
		ReadFromPropertiesFiles readFromPropertiesFiles1 = ReadFromPropertiesFiles.getInstance();
		String key5 = readFromPropertiesFiles1.getPropertyMap().get("fileUploadDirir");
		String key6 = readFromPropertiesFiles1.getPropertyMap().get("abc");
		String key7 = readFromPropertiesFiles1.getPropertyMap().get("qwerty");
		String key8 = readFromPropertiesFiles1.getPropertyMap().get("lank");

		ReadFromPropertiesFiles readFromPropertiesFiles2 = ReadFromPropertiesFiles.getInstance();
		String key9 = readFromPropertiesFiles2.getPropertyMap().get("fileUploadDirir");
		String key10 = readFromPropertiesFiles2.getPropertyMap().get("abc");
		String key11 = readFromPropertiesFiles2.getPropertyMap().get("qwerty");
		String key12 = readFromPropertiesFiles2.getPropertyMap().get("lank");
		
		// fileDownloadFromClientFromKey();

		fileUploadJsonFile2();

		//System.out.println(map);

		return Response.status(Response.Status.OK).entity(author).build();
	}

	// https://howtodoinjava.com/jersey/jersey-file-upload-example/
	public void fileUploadJsonFile() throws IOException {
		final Client client = ClientBuilder.newBuilder().register(MultiPartFeature.class).build();

		final FileDataBodyPart filePart = new FileDataBodyPart("file",
				new File("D:\\InfyProject\\readFile\\20191208142857_ExcludeFile.txt"));
		FormDataMultiPart formDataMultiPart = new FormDataMultiPart();
		final FormDataMultiPart multipart = (FormDataMultiPart) formDataMultiPart.field("barometerId", "1234")
				.field("messageId", "asdfsda").bodyPart(filePart);

		final WebTarget target = client.target("http://localhost:8082/uploadFile");
		final Response response = target.request().post(Entity.entity(multipart, multipart.getMediaType()));

		// Use response object to verify upload success

		formDataMultiPart.close();
		multipart.close();
	}

	public void fileUploadJsonFile2() throws IOException {
		final Client client = ClientBuilder.newBuilder().register(MultiPartFeature.class).build();

		File initialFile = new File("D:\\InfyProject\\readFile\\20191208142857_ExcludeFile.txt");
		InputStream targetStream = new FileInputStream(initialFile);
		StreamDataBodyPart streamDataBodyPart = new StreamDataBodyPart("file", targetStream);

		MultiPart multipartEntity = new FormDataMultiPart().field("barometerId", "1234").field("messageId", "asdfsda")
				.bodyPart(streamDataBodyPart);

		Builder builder = client.target("http://localhost:8082/uploadFile").request().header("Authorization",
				"Basic bml0aXNoOmtyaXNobmE=");

		Invocation invocation = builder.buildPost(Entity.entity(multipartEntity, MediaType.MULTIPART_FORM_DATA));
		Response response = invocation.invoke();

		String respContent = response.readEntity(String.class);

		// final Response response =
		// target.request().post(Entity.entity(multipartEntity,
		// MediaType.MULTIPART_FORM_DATA));

		System.out.println(respContent);

	}

	// https://stackoverflow.com/questions/10587561/password-protected-zip-file-in-java/32253028#32253028
	public long fileDownloadFromClientFromKey() {
		long bytesCopied = 0;
		// Path out = Paths.get(this.fileInfo.getLocalPath());

		try {

			Client restClient = ClientBuilder.newClient();
			WebTarget webTarget = restClient.target("http://localhost:8082/downloadFile/case_SingleValueFile.txt");
			Invocation.Builder invocationBuilder = webTarget.request(MediaType.TEXT_PLAIN_TYPE);

			Response response = invocationBuilder.get();

			if (response.getStatus() != 200) {
				System.out.println("HTTP status " + response.getStatus());
				// return bytesCopied;
			}

			byte[] bytes = response.readEntity(byte[].class);
			// bytesCopied = Files.copy(in, out, REPLACE_EXISTING);

			String str = new String(bytes, "UTF-8");

			System.out.println(str);

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		return bytesCopied;
	}

}
