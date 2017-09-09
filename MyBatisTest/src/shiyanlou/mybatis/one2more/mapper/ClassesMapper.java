package shiyanlou.mybatis.one2more.mapper;

import shiyanlou.mybatis.one2more.model.Classes;

public interface ClassesMapper {

	/**
	 * 根据ID查询班级Classes和它的学生
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Classes selectClassAndStudentById(Integer id) throws Exception;
}
