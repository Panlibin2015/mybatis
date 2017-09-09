package shiyanlou.mybatis.one2one.mapper;

import shiyanlou.mybatis.one2one.model.Classes;

public interface ClassesMapper {
	/**
	 * 根据id查询班级Classes
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Classes selectClassById(Integer id) throws Exception;
}
