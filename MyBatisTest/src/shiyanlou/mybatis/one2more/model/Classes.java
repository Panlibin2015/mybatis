package shiyanlou.mybatis.one2more.model;

import java.util.List;

import org.apache.ibatis.type.Alias;
@Alias("Classes2")
public class Classes {
	private Integer id;
	private String name;
	// 班级和学生是一对多的关系，即一个班级可以有多个学生
	private List<Student> students;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	@Override
	public String toString() {
		return "Classes [id=" + id + ", name=" + name + ", students=" + students + "]";
	}

}
