package com.cg.nordea.service.impl;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.nordea.dto.CertCategoryDetailsDto;
import com.cg.nordea.dto.CertResourceDetails;
import com.cg.nordea.dto.CertResourceMappingDto;
import com.cg.nordea.dto.CertificationDetailsDto;
import com.cg.nordea.dto.ResponseDTO;
import com.cg.nordea.entities.CertCatergoryDetails;
import com.cg.nordea.entities.CertificationDetailsOfResource;
import com.cg.nordea.entities.Currency;
import com.cg.nordea.entities.ResourceDetails;
import com.cg.nordea.exceptions.NoDataFoundException;
import com.cg.nordea.repositories.CertCatergoryDetailsRepository;
import com.cg.nordea.repositories.CertificationDetailsOfResourceRepository;
import com.cg.nordea.repositories.CurrencyRepository;
import com.cg.nordea.repositories.ResourceDetailsRepository;
import com.cg.nordea.service.ComKartService;

@Service
public class ComKartServiceImpl implements ComKartService {

	@Autowired
	CurrencyRepository CurrencyRepository;

	@Autowired
	ResourceDetailsRepository resourceDetailsRepository;

	@Autowired
	CertCatergoryDetailsRepository certCatergoryDetailsRepository;

	@Autowired
	CertificationDetailsOfResourceRepository certificationDetailsOfResourceRepository;

	@Autowired
	private ModelMapper modelMapper;

	public List<Currency> getCurrency() {
		return (List<Currency>) CurrencyRepository.findAll();
	}

	@Override
	public ResourceDetails getResource(String employeeID) throws NoDataFoundException {

		return resourceDetailsRepository.findById(employeeID)
				.orElseThrow(() -> new NoDataFoundException("User not present with " + employeeID));

	}

	@Override
	public CertResourceDetails getCertificates(String employeeID) throws NoDataFoundException {
		ResourceDetails resourceDetails = resourceDetailsRepository.findById(employeeID)
				.orElseThrow(() -> new NoDataFoundException("User not present with " + employeeID));

		CertResourceDetails certResourceDetails = modelMapper.map(resourceDetails, CertResourceDetails.class);

		List<CertificationDetailsOfResource> certList = certificationDetailsOfResourceRepository
				.findByEmployeeID(employeeID)
				.orElseThrow(() -> new NoDataFoundException("Certificate details not available for " + employeeID));

		List<CertificationDetailsDto> certDetailDtoList = certList.stream().map(certDetails -> {
			try {
				return loadCertificationData(certDetails);
			} catch (NoDataFoundException e) {
				e.printStackTrace();
			}
			return null;
		}).collect(Collectors.toList());

		certResourceDetails.setCertificationList(certDetailDtoList);
		certResourceDetails.setResponseCode("200");
		certResourceDetails.setResponseMessage("Success");
		return certResourceDetails;
	}

	public CertificationDetailsDto loadCertificationData(CertificationDetailsOfResource certDetail)
			throws NoDataFoundException {
		CertificationDetailsDto certDetailsDto = new CertificationDetailsDto();
		CertCatergoryDetails categoryDetails = certCatergoryDetailsRepository.findById(certDetail.getCertCategoryId())
				.orElseThrow(() -> new NoDataFoundException(
						"Certificate details not available for " + certDetail.getEmployeeID()));

		certDetailsDto.setProvider(categoryDetails.getCertProvider());
		certDetailsDto.setTechName(categoryDetails.getTechName());
		certDetailsDto.setCertificateName(categoryDetails.getCertName());
		certDetailsDto.setCertificationDate(certDetail.getCertificationDate());
		certDetailsDto.setValidFrom(certDetail.getValidFromDate());
		certDetailsDto.setValidTo(certDetail.getValidToDate());

		return certDetailsDto;

	}

	@Override
	public List<CertCategoryDetailsDto> getCertCategoryDetails() throws NoDataFoundException {

		List<CertCatergoryDetails> categoryDetails = (List<CertCatergoryDetails>) 
				certCatergoryDetailsRepository.findAll();

		List<CertCategoryDetailsDto> certDetailDtoList = categoryDetails.stream()
				.map(categoryDetail -> 
					modelMapper.map(categoryDetail, CertCategoryDetailsDto.class)
		).collect(Collectors.toList());

		return certDetailDtoList;
	}


	@Override
	@Transactional
	public ResponseDTO saveCertResourceMapping(CertResourceMappingDto certResourceMappingDto) throws Exception {
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			CertificationDetailsOfResource certificationDetailsOfResource = loadCertificationDetailsOfResource(
					certResourceMappingDto);
			certificationDetailsOfResourceRepository.save(certificationDetailsOfResource);
			responseDTO.setMessage("Certificate Successfuly saved");
			responseDTO.setCode(200);
		} catch (Exception e) {
			responseDTO.setMessage("Error in saving Certificate Details");
			responseDTO.setCode(500);
		}
		return responseDTO;

	}

	private CertificationDetailsOfResource loadCertificationDetailsOfResource(
			CertResourceMappingDto certResourceMappingDto) {
		CertificationDetailsOfResource certificationDetailsOfResource = modelMapper.map(certResourceMappingDto, CertificationDetailsOfResource.class);
		LocalDateTime now = LocalDateTime.now();
		certificationDetailsOfResource.setCreatedDate(now);
		certificationDetailsOfResource.setUpdatedDate(now);
		certificationDetailsOfResource.setResourceCertId(null);
		return certificationDetailsOfResource;
	}

	@Override
	public ByteArrayOutputStream getExcelResourceCertificates() throws Exception {

		List<String> empIds = certificationDetailsOfResourceRepository.findCertEmpId().get();

		List<CertResourceDetails> lstCertResourceDetails = new ArrayList<>();

		empIds.forEach(empId -> {
			try {
				lstCertResourceDetails.add(getCertificates(empId));
			} catch (NoDataFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		XSSFWorkbook workbook = formatExcelData(lstCertResourceDetails); // creates the workbook
		workbook.write(stream);
		workbook.close();

		return stream;
	}

	private XSSFWorkbook formatExcelData(List<CertResourceDetails> lstCertResourceDetails) {

		XSSFWorkbook workbook = new XSSFWorkbook();

		Sheet sheet = workbook.createSheet("Certificates");

		createHeader(workbook, sheet);
		addData(workbook, sheet, lstCertResourceDetails);

		return workbook;
	}

	private void createHeader(XSSFWorkbook workbook, Sheet sheet) {
		Row header = sheet.createRow(0);

		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.getIndex());
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerStyle.setBorderTop(BorderStyle.MEDIUM);
		headerStyle.setBorderBottom(BorderStyle.MEDIUM);
		headerStyle.setBorderLeft(BorderStyle.MEDIUM);
		headerStyle.setBorderRight(BorderStyle.MEDIUM);

		XSSFFont font = workbook.createFont();
		font.setFontName("Arial");
		font.setFontHeightInPoints((short) 10);
		font.setBold(true);
		headerStyle.setFont(font);

		int headerCellNumber = 0;
		List<String> lstHeader = new ArrayList<>();

		lstHeader.add("Resource Name");
		lstHeader.add("Certificate Name");
		lstHeader.add("Technology Name");
		lstHeader.add("Certificate Provider");
		lstHeader.add("Certification Date");
		lstHeader.add("Certificate Valid From");
		lstHeader.add("Certificate Valid To");

		Cell headerCell = null;
		for (String strHeader : lstHeader) {
			headerCell = header.createCell(headerCellNumber);
			headerCell.setCellValue(strHeader);
			headerCell.setCellStyle(headerStyle);
			sheet.autoSizeColumn(headerCellNumber);
			headerCellNumber++;
		}
	}

	private void addData(XSSFWorkbook workbook, Sheet sheet, List<CertResourceDetails> lstCertResourceDetails) {

		int rowNum = 1;
		for (CertResourceDetails certResourceDetails : lstCertResourceDetails) {
			for (CertificationDetailsDto ceartficate : certResourceDetails.getCertificationList()) {

				int cellNumber = 0;
				Row header = sheet.createRow(rowNum);

				CellStyle dataStyle = workbook.createCellStyle();
				dataStyle.setWrapText(true);
				dataStyle.setBorderTop(BorderStyle.MEDIUM);
				dataStyle.setBorderBottom(BorderStyle.MEDIUM);
				dataStyle.setBorderLeft(BorderStyle.MEDIUM);
				dataStyle.setBorderRight(BorderStyle.MEDIUM);

				XSSFFont font = workbook.createFont();
				font.setFontName("Arial");
				font.setFontHeightInPoints((short) 8);
				font.setBold(true);
				dataStyle.setFont(font);

				// Resource Name
				Cell headerCell = header.createCell(cellNumber);
				headerCell.setCellValue(certResourceDetails.getEmployeeName());
				headerCell.setCellStyle(dataStyle);
				cellNumber++;
				
				// Certificate Name
				headerCell = header.createCell(cellNumber);
				headerCell.setCellValue(ceartficate.getCertificateName());
				headerCell.setCellStyle(dataStyle);
				cellNumber++;

				// Technology Name
				headerCell = header.createCell(cellNumber);
				headerCell.setCellValue(ceartficate.getTechName());
				headerCell.setCellStyle(dataStyle);
				cellNumber++;

				// Certificate Provider
				headerCell = header.createCell(cellNumber);
				headerCell.setCellValue(ceartficate.getProvider());
				headerCell.setCellStyle(dataStyle);
				cellNumber++;

				// CErtification Date
				headerCell = header.createCell(cellNumber);
				headerCell.setCellValue(ceartficate.getCertificationDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
				headerCell.setCellStyle(dataStyle);
				cellNumber++;

				// Certificate Valid From Date
				headerCell = header.createCell(cellNumber);
				if (null != ceartficate.getValidFrom()) {
					headerCell.setCellValue(ceartficate.getValidFrom().format(DateTimeFormatter.ISO_LOCAL_DATE));
				}
				headerCell.setCellStyle(dataStyle);
				cellNumber++;

				// Certificate Valid To Date
				headerCell = header.createCell(cellNumber);
				if (null != ceartficate.getValidTo()) {
					headerCell.setCellValue(ceartficate.getValidTo().format(DateTimeFormatter.ISO_LOCAL_DATE));
				}
				headerCell.setCellStyle(dataStyle);
				cellNumber++;

				rowNum++;
			}
		}

	}

}
