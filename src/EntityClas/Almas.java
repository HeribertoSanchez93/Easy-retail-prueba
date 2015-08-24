//Paquete
package EntityClas;

//Importaciones
import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import ptovta.Star;

/**
 *
 * @author Hp
 */
@Entity
@Table(name = "almas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Almas.findAll", query = "SELECT a FROM Almas a"),
    @NamedQuery(name = "Almas.findByIdId", query = "SELECT a FROM Almas a WHERE a.idId = :idId"),
    @NamedQuery(name = "Almas.findByAlma", query = "SELECT a FROM Almas a WHERE a.alma = :alma"),
    @NamedQuery(name = "Almas.findByRespon", query = "SELECT a FROM Almas a WHERE a.respon = :respon"),
    @NamedQuery(name = "Almas.findByAlmadescrip", query = "SELECT a FROM Almas a WHERE a.almadescrip = :almadescrip"),
    @NamedQuery(name = "Almas.findByDir1", query = "SELECT a FROM Almas a WHERE a.dir1 = :dir1"),
    @NamedQuery(name = "Almas.findByDir2", query = "SELECT a FROM Almas a WHERE a.dir2 = :dir2"),
    @NamedQuery(name = "Almas.findByDir3", query = "SELECT a FROM Almas a WHERE a.dir3 = :dir3"),
    @NamedQuery(name = "Almas.findByActfij", query = "SELECT a FROM Almas a WHERE a.actfij = :actfij"),
    @NamedQuery(name = "Almas.findByEstac", query = "SELECT a FROM Almas a WHERE a.estac = :estac"),
    @NamedQuery(name = "Almas.findBySucu", query = "SELECT a FROM Almas a WHERE a.sucu = :sucu"),
    @NamedQuery(name = "Almas.findByNocaj", query = "SELECT a FROM Almas a WHERE a.nocaj = :nocaj"),
    @NamedQuery(name = "Almas.findByExport", query = "SELECT a FROM Almas a WHERE a.export = :export"),
    @NamedQuery(name = "Almas.findByExtr1", query = "SELECT a FROM Almas a WHERE a.extr1 = :extr1"),
    @NamedQuery(name = "Almas.findByExtr2", query = "SELECT a FROM Almas a WHERE a.extr2 = :extr2"),
    @NamedQuery(name = "Almas.findByExtr3", query = "SELECT a FROM Almas a WHERE a.extr3 = :extr3"),
    @NamedQuery(name = "Almas.findByFalt", query = "SELECT a FROM Almas a WHERE a.falt = :falt"),
    @NamedQuery(name = "Almas.findByFmod", query = "SELECT a FROM Almas a WHERE a.fmod = :fmod")})
public class Almas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_id")
    private Integer idId;
    @Basic(optional = false)
    @Column(name = "alma")
    private String alma;
    @Basic(optional = false)
    @Column(name = "respon")
    private String respon;
    @Basic(optional = false)
    @Column(name = "almadescrip")
    private String almadescrip;
    @Column(name = "dir1")
    private String dir1;
    @Column(name = "dir2")
    private String dir2;
    @Column(name = "dir3")
    private String dir3;
    @Column(name = "actfij")
    private Boolean actfij;
    @Basic(optional = false)
    @Column(name = "estac")
    private String estac;
    @Basic(optional = false)
    @Column(name = "sucu")
    private String sucu;
    @Basic(optional = false)
    @Column(name = "nocaj")
    private String nocaj;
    @Column(name = "export")
    private Boolean export;
    @Column(name = "extr1")
    private String extr1;
    @Column(name = "extr2")
    private String extr2;
    @Column(name = "extr3")
    private String extr3;
    @Column(name = "falt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date falt;
    @Column(name = "fmod")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fmod;

    //Contiene el resultado de las consultas    
    private List<EntityClas.Almas> lstResultG;
    
    
    
    
    public Almas() {
    }

    public Almas(Integer idId) {
        this.idId = idId;
    }

    public Almas(Integer idId, String alma, String respon, String almadescrip, String estac, String sucu, String nocaj) {
        this.idId = idId;
        this.alma = alma;
        this.respon = respon;
        this.almadescrip = almadescrip;
        this.estac = estac;
        this.sucu = sucu;
        this.nocaj = nocaj;
    }

    public Integer getIdId() {
        return idId;
    }

    public void setIdId(Integer idId) {
        this.idId = idId;
    }

    public String getAlma() {
        return alma;
    }

    public void setAlma(String alma) {
        this.alma = alma;
    }

    public String getRespon() {
        return respon;
    }

    public void setRespon(String respon) {
        this.respon = respon;
    }

    public String getAlmadescrip() {
        return almadescrip;
    }

    public void setAlmadescrip(String almadescrip) {
        this.almadescrip = almadescrip;
    }

    public String getDir1() {
        return dir1;
    }

    public void setDir1(String dir1) {
        this.dir1 = dir1;
    }

    public String getDir2() {
        return dir2;
    }

    public void setDir2(String dir2) {
        this.dir2 = dir2;
    }

    public String getDir3() {
        return dir3;
    }

    public void setDir3(String dir3) {
        this.dir3 = dir3;
    }

    public Boolean getActfij() {
        return actfij;
    }

    public void setActfij(Boolean actfij) {
        this.actfij = actfij;
    }

    public String getEstac() {
        return estac;
    }

    public void setEstac(String estac) {
        this.estac = estac;
    }

    public String getSucu() {
        return sucu;
    }

    public void setSucu(String sucu) {
        this.sucu = sucu;
    }

    public String getNocaj() {
        return nocaj;
    }

    public void setNocaj(String nocaj) {
        this.nocaj = nocaj;
    }

    public Boolean getExport() {
        return export;
    }

    public void setExport(Boolean export) {
        this.export = export;
    }

    public String getExtr1() {
        return extr1;
    }

    public void setExtr1(String extr1) {
        this.extr1 = extr1;
    }

    public String getExtr2() {
        return extr2;
    }

    public void setExtr2(String extr2) {
        this.extr2 = extr2;
    }

    public String getExtr3() {
        return extr3;
    }

    public void setExtr3(String extr3) {
        this.extr3 = extr3;
    }

    public Date getFalt() {
        return falt;
    }

    public void setFalt(Date falt) {
        this.falt = falt;
    }

    public Date getFmod() {
        return fmod;
    }

    public void setFmod(Date fmod) {
        this.fmod = fmod;
    }

    
    //Catálogo 1
    public int iCat1()
    {
        //Inicia la sesión inicial
        SessionFactory sesFacto; 
        try
        {                        
            sesFacto = new AnnotationConfiguration().addPackage(EntityClas.Almas.class.getPackage().getName()).addAnnotatedClass(EntityClas.Almas.class).configure().buildSessionFactory();
        }
        catch(Exception expnExcep)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnExcep.getMessage(), Star.sErrSQL, expnExcep.getStackTrace());
            return -1;                        
        }                
        
        //Abre la sesión
        Session sesOpen;
        try
        {
            //Abre la sesión
            sesOpen = sesFacto.openSession();
        }
        catch(Exception expnExcep)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnExcep.getMessage(), Star.sErrSQL, expnExcep.getStackTrace());
            return -1;                        
        }                
        
        //Inicia la transacción
        Transaction transHiber;
        try
        {                                    
            transHiber  = sesOpen.beginTransaction();                                                
        }
        catch(Exception expnExcep)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnExcep.getMessage(), Star.sErrSQL, expnExcep.getStackTrace());
            return -1;                        
        }            
        
        //Guarda el resultado globalmente
        try
        {            
            lstResultG = sesOpen.createQuery("FROM " + EntityClas.Almas.class.getName()).list();
        }            
        catch(Exception expnExcep)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnExcep.getMessage(), Star.sErrSQL, expnExcep.getStackTrace());
            return -1;                        
        }
        
        for(Iterator iterator = lstResultG.iterator(); iterator.hasNext();)
        {
            EntityClas.Almas employee = (EntityClas.Almas) iterator.next(); 
            javax.swing.JOptionPane.showMessageDialog(null, employee.getAlma(), "", javax.swing.JOptionPane.INFORMATION_MESSAGE, null);
        }
                           
        //Termina la transacción
        try
        {
            transHiber.commit();    
        }
        catch(Exception expnExcep)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnExcep.getMessage(), Star.sErrSQL, expnExcep.getStackTrace());
            return -1;                        
        }            

        //Cierra la sesión
        try
        {
            sesOpen.close();            
        }
        catch(Exception expnExcep)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnExcep.getMessage(), Star.sErrSQL, expnExcep.getStackTrace());
            return -1;                        
        }                             
        
        //Regresa que todo fue bien
        return 0;
        
    }//Fin de public int iCat1()
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idId != null ? idId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Almas)) {
            return false;
        }
        Almas other = (Almas) object;
        if ((this.idId == null && other.idId != null) || (this.idId != null && !this.idId.equals(other.idId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EntityClas.Almas[ idId=" + idId + " ]";
    }
    
}
