/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Pdf;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class PdfServiceTests {
	
	@Autowired
	protected PdfService pdfService;

	private static int TEST_PDF_ID = 1;


//	@BeforeEach
//	void setup() {
//		pdf.setArchivo("Archivo");
//		pdf.setId(pdfId);
//		
//		Pdf pdf1 = new Pdf();
//		pdf1.setArchivo("Archivo");
//		pdf1.setId(pdfId);
//
//		Pdf pdf2 = new Pdf();
//		pdf2.setArchivo("Otro archivo");
//		pdf2.setId(pdfId + 1);
//
//		Pdf pdf3 = new Pdf();
//		pdf3.setArchivo("Documento");
//		pdf3.setId(pdfId + 2);
//
//		Pdf pdf4 = new Pdf();
//		pdf4.setArchivo("Documento3");
//		pdf4.setId(pdfId + 3);
//
//		Pdf pdf5 = new Pdf();
//		pdf5.setArchivo("Documentaso");
//		pdf5.setId(pdfId + 4);
//
//		Pdf pdf6 = new Pdf();
//		pdf6.setArchivo("Archivao");
//		pdf6.setId(pdfId + 5);
//
//		Pdf pdf7 = new Pdf();
//		pdf7.setArchivo("Ejemplo");
//		pdf7.setId(pdfId + 6);
//
//		Pdf pdf8 = new Pdf();
//		pdf8.setArchivo("Ejemplo2");
//		pdf8.setId(pdfId + 7);
//
//		given(this.pdfService.findById(pdfId)).willReturn(pdf1);
//		given(this.pdfService.findAll()).willReturn(Lists.newArrayList(pdf1, pdf2, 
//				pdf3, pdf4, pdf5, pdf6, pdf7, pdf8));
//
//		
//	}
	@DisplayName("Prueba de localización de pdf")
	@Test
	void shouldFindPdfWithCorrectId() {
		Pdf pdf = this.pdfService.findById(TEST_PDF_ID);
		assertEquals(TEST_PDF_ID, pdf.getId());

	}

//	@Test
//	void shouldFindAllPdf() {
//		Collection<Pdf> pdf = this.pdfService.findAll();
//
//		Pdf pdf1 = EntityUtils.getById(pdf, Pdf.class, 1);
//		assertThat(pdf1.getArchivo()).isEqualTo("Archivo");
//		Pdf pdf4 = EntityUtils.getById(pdf, Pdf.class, 4);
//		assertThat(pdf4.getArchivo()).isEqualTo("Documento3");
//	}
	@DisplayName("Prueba de guardado de pdf")
	@Test
	@Transactional
	public void shouldsavePdf() {

		Pdf pdf = new Pdf();
		pdf.setLink("ejemplo");
		pdf.setNombre("jnoj");
		this.pdfService.save(pdf);
		assertEquals(pdf, this.pdfService.findById(pdf.getId()));
	}
	@DisplayName("Prueba de actualización de pdf")
	@Test
	@Transactional
	public void shouldUpdatePdfName() throws Exception {
		Pdf pdf = this.pdfService.findById(1);
		String oldName = pdf.getLink();

		String newName = oldName + "X";
		pdf.setLink(newName);
		this.pdfService.save(pdf);

		pdf = this.pdfService.findById(1);
		assertThat(pdf.getLink()).isEqualTo(newName);
	}

	@DisplayName("Prueba de borrado de pdf")
	@Test
	void shouldDelete() {
		
		
		this.pdfService.delete(this.pdfService.findById(TEST_PDF_ID));
		assertThrows(NullPointerException.class, () -> this.pdfService.findById(TEST_PDF_ID).getLink());
		

	}
	
	
}
