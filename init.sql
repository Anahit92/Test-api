DROP TABLE IF EXISTS public.notification;
DROP TABLE IF EXISTS public.comment;
CREATE TABLE public.comment
(
    id uuid NOT NULL,
    comment text COLLATE pg_catalog."default",
    "time" timestamp with time zone,
    CONSTRAINT comments_pkey PRIMARY KEY (id)
);

CREATE TABLE public.notification
(
    id uuid NOT NULL,
    comment_id uuid NOT NULL,
    "time" timestamp with time zone,
    delivered boolean,
    CONSTRAINT notification_pkey PRIMARY KEY (id),
    CONSTRAINT notification_comment_id_fkey FOREIGN KEY (comment_id)
        REFERENCES public.comment (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)


