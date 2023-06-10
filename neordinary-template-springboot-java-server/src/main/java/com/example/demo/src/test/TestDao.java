package com.example.demo.src.test;

import com.example.demo.src.test.entity.Memo;
import com.example.demo.src.test.model.GetMemoDto;
import com.example.demo.src.test.model.MemoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.query.JpaQueryMethodFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class TestDao {

    private final EntityManager entityManager;

    public void createMemo(Memo memo){

        if(memo.getId() == null) {
            entityManager.persist(memo);
        }else {
            entityManager.merge(memo);
        }
    }

    public List<Memo> checkMemo(String memo){
        return entityManager.createQuery("select m from Memo m where m.memo = :memo", Memo.class)
                .setParameter("memo", memo)
                .getResultList();

    }

    public List<Memo> getMemos() {
        return entityManager.createQuery("select m from Memo m", Memo.class)
                .getResultList();
    }

    public Memo getMemo(Long id) {
        return entityManager.find(Memo.class, id);
    }


}
