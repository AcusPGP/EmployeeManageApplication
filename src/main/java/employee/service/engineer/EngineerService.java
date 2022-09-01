package employee.service.engineer;


import employee.pojo.Engineer;

public interface EngineerService<T extends Engineer>{

    public void showAllDegree(Engineer engineer) ;
}
