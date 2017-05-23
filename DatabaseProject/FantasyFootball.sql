UPDATE Squad q
        INNER JOIN
    (SELECT 
        s.SquadID, SUM(p.Points) AS summa
    FROM
        Player p, Players_In_Squad s
    WHERE
        p.PlayerID = s.PlayerID
            AND s.Major = 'MAI'
    GROUP BY s.SquadID) AS a ON q.SquadID = a.SquadID 
SET 
    q.Week = a.summa