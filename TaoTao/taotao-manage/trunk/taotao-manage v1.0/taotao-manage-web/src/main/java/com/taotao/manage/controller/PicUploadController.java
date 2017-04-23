package com.taotao.manage.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.bean.PicUploadResult;
import com.taotao.manage.service.PropertieService;

@Controller
@RequestMapping("/pic")
public class PicUploadController {
	private static final ObjectMapper mapper = new ObjectMapper();
	// 允许上传的图片格式
	private static final String[] IMAGE_TYPE = { ".jpg", ".png", ".bpm", ".jpeg", ".gif" };
	@Autowired
	private PropertieService propertieService;

	// 需要设置返回json数据的文本格式问text/plain
	@RequestMapping(value = "/upload", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String upload(@RequestParam("uploadFile") MultipartFile uploadFile, HttpServletResponse response)
			throws Exception {
		// 定义校验图片格式
		boolean isLegal = false;
		// 校验图片的格式是不是允许上传的格式
		for (String imageType : IMAGE_TYPE) {
			if (uploadFile != null && StringUtils.endsWithIgnoreCase(uploadFile.getOriginalFilename(), imageType)) {
				isLegal = true;
				break;
			}
		}
		PicUploadResult picUploadResult = new PicUploadResult();
		// 设置上传的结果,如果上传成功设置结果为0,上传失败设置error为1
		picUploadResult.setError(isLegal ? 0 : 1);
		// 获取文件保存路径
		String filePath = getFilePath(uploadFile.getOriginalFilename());
		// 图片的访问路径
		String picUrl = StringUtils.replace(StringUtils.substringAfter(filePath, propertieService.REPOSITORY_PATH),
				"\\", "/");
		picUploadResult.setUrl(propertieService.IMAGE_BASE_URL + picUrl);
		File newFile = new File(filePath);
		if (uploadFile != null) {
			uploadFile.transferTo(newFile); // 保存到新文件中
		}
		// 校验图片是否合法,校验图片内容,获取图片的长和宽,获取到值即为图片,获取不到则不是图片
		isLegal = false;
		try {
			BufferedImage image = ImageIO.read(newFile);
			if (image != null) {
				int height = image.getHeight();
				int width = image.getWidth();
				if (height > 0 && width > 0) {
					isLegal = true;
					picUploadResult.setHeight(height + "");
					picUploadResult.setWidth(width + "");
				}
			}
		} catch (Exception e) {
		}
		// 如果数据不合法,删除文件
		if (!isLegal)
			newFile.delete();
		picUploadResult.setError(isLegal ? 0 : 1);
		return mapper.writeValueAsString(picUploadResult);
	}
	private String getFilePath(String originalFilename) {
		// 进行生成文件保存目录
		String baseFolder = propertieService.REPOSITORY_PATH + File.separator + "images";
		Date nowDate = new Date();
		// yyyy/MM/dd
		String fileFolder = baseFolder + File.separator + new DateTime(nowDate).toString("yyyy") + File.separator
				+ new DateTime(nowDate).toString("MM") + File.separator + new DateTime(nowDate).toString("dd");
		File file = new File(fileFolder);
		if (!file.isDirectory()) {
			// 如果目录不存在，则创建目录
			file.mkdirs();
		}
		// 生成新的文件名
		String fileName = new DateTime(nowDate).toString("yyyyMMddhhmmssSSSS") + RandomUtils.nextInt(100, 9999) + "."
				+ StringUtils.substringAfterLast(originalFilename, ".");
		return fileFolder + File.separator + fileName;
	}
}
