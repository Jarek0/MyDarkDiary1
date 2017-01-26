
package myDarkDiary.service.model;


import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user")
public class User{
    
   private Long id;
    private String username;
    private String password;
    private String email;
    private boolean enabled;
    private boolean online;
    private boolean banned;
    private String passwordConfirm;
    private VerificationToken token;
    private Set<Role> roles;
    private Set<User> users;
    //user profile information
    private Image avatar;
    private String discription;
    private Set<Message> sended;
    private Set<Message> received;
    private List<Conversation> conversations;
    
    public User(){
        
    }
    public User(String username,String password,String email,boolean enabled,boolean online, boolean banned,VerificationToken token,Set<Role> roles,Set<User> users,Image avatar,String discription,Set<Message> sended,Set<Message> received,List<Conversation> conversations){
        this.username=username;
        this.password=password;
        this.email=email;
        this.enabled=enabled;
        this.online=online;
        this.banned=banned;
        this.passwordConfirm=passwordConfirm;
        this.discription=discription;
        this.token=token;
        this.roles=roles;
        this.users=users;
        this.avatar=avatar;
        this.sended=sended;
        this.received=received;
        this.conversations=conversations;
    }
    public User(String username,String password,String email,boolean enabled,boolean online, boolean banned,Set<Role> roles){
        this.username=username;
        this.password=password;
        this.email=email;
        this.enabled=enabled;
        this.online=online;
        this.banned=banned;
        this.passwordConfirm=passwordConfirm;
        this.roles=roles;
        
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    public boolean getOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
    
    public boolean getBanned() {
        return banned;
    }
    
    public void setBanned(boolean banned) {
        
        this.banned = banned;
    }


    @Transient
    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }
    
    

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_user", joinColumns = @JoinColumn(name = "user_id1"), inverseJoinColumns = @JoinColumn(name = "user_id2"))
    public Set<User> getUsers() {
        return users;
    }
    
    public void setUsers(Set<User> users) {
        this.users = users;
    }
   
    public boolean isFriend(Long userId){
        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()) {
        User element = iterator.next();
        if (Objects.equals(element.getId(), userId)) {
        return true;
        }
        }
        return false;
    }
    @OneToOne(mappedBy = "user")
    public VerificationToken getToken() {
        return token;
    }

    public void setToken(VerificationToken token) {
        this.token = token;
    }
    
    @OneToOne(mappedBy = "userId")
    public Image getAvatar() {
        return avatar;
    }

    public void setAvatar(Image avatar) {
        this.avatar = avatar;
    }
    
    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

     @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL)
    public Set<Message> getReceived() {
        return received;
    }

    public void setReceived(Set<Message> received) {
        this.received = received;
    }
    
    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    public Set<Message> getSended() {
        return sended;
    }

    public void setSended(Set<Message> sended) {
        this.sended = sended;
    }
    
    @ManyToMany(fetch = FetchType.EAGER,mappedBy = "users")
    public List<Conversation> getConversations() {
        return conversations;
    }

    public void setConversations(List<Conversation> conversations) {
        this.conversations = conversations;
    }
}
